package com.example.cherrypickserver.chat.application;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.article.domain.ArticleRepository;
import com.example.cherrypickserver.article.exception.ArticleNotFoundException;
import com.example.cherrypickserver.chat.domain.*;
import com.example.cherrypickserver.chat.dto.response.GptResponse;
import com.example.cherrypickserver.chat.dto.request.ChatRequest;
import com.example.cherrypickserver.chat.dto.request.QuestionRequest;
import com.example.cherrypickserver.chat.dto.response.ChatResponse;
import com.example.cherrypickserver.chat.exception.ChatNotFoundException;
import com.example.cherrypickserver.chat.exception.ChatSelectNotFoundException;
import com.example.cherrypickserver.chat.mapper.*;
import com.example.cherrypickserver.member.domain.Member;
import com.example.cherrypickserver.member.domain.MemberRepository;
import com.example.cherrypickserver.member.exception.MemberNotFoundException;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final ChatRepository chatRepository;
    private final ChatContentRepository chatContentRepository;
    private final ChatSelectRepository chatSelectRepository;

    private final ChatMapper chatMapper;
    private final ChatContentMapper chatContentMapper;
    private final ChatCompletionMapper chatCompletionMapper;
    private final ChatSelectMapper chatSelectMapper;

    private final OpenAiService openAiService;

    @Override
    public ChatResponse createChatAndContent(ChatRequest chatRequest) {

        Member member = memberRepository.findByIdAndIsEnable(chatRequest.getMemberId(), true)
                .orElseThrow(MemberNotFoundException::new);
        Article article = articleRepository.findByIdAndIsEnable(chatRequest.getArticleId(), true)
                .orElseThrow(ArticleNotFoundException::new);

        Chat chat = chatRepository.save(chatMapper.toEntity(member, article));

        //시스템 설정용 chatContent
        String content = "당신은 " + article.getIndustry().getValueGpt()
                + " 분야 기사 질문에 대답하기 위해 개발된 모델인 '취트키GPT' 입니다. "
                + "모든 답변은 10초 이내로, 분야와 관련 없는 질문은 답변하지 마세요. "
                + "영어 외 언어로의 기사 번역은 하지 마세요. "
                + "질문자가 읽은 기사 내용은 다음과 같습니다. "
                + article.getContents();
        chatContentRepository.save(chatContentMapper.toEntity("system", content, chat));

        return chatMapper.fromEntity(chat);
    }

    @Override
    public GptResponse chatQuestion(QuestionRequest questionRequest) {

        Chat chat = chatRepository.findById(questionRequest.getChatId())
                .orElseThrow(ChatNotFoundException::new);
        List<ChatContent> chatContentList = chatContentRepository.findAllByChatAndIsEnable(chat, true, Sort.by("createdAt"));

        //Chat GPT에 넘겨줄 이전 대화 목록
        List<ChatMessage> chatMessageList = chatContentList.stream()
                .map(chatContent -> new ChatMessage(chatContent.getRole(), chatContent.getContent()))
                .collect(Collectors.toList());

        chatMessageList.add(new ChatMessage("user", questionRequest.getQuestion()));

        ChatCompletionRequest chatCompletionRequest = chatCompletionMapper.fromEntity(chatMessageList);
        ChatCompletionResult chatCompletionResult = openAiService.createChatCompletion(chatCompletionRequest);

//        System.out.println("completion token : " + chatCompletionResult.getUsage().getCompletionTokens());
//        System.out.println("total token : " + chatCompletionResult.getUsage().getTotalTokens());
        //대화 길이 계산 및 필요시 생략
        if (chatCompletionResult.getUsage().getTotalTokens() > 3000) {
            chatContentList.get(1).delete();
            chatContentList.get(2).delete();
        }

        //대화 내용 저장
        ChatContent question = chatContentMapper.toEntity("user", questionRequest.getQuestion(), chat);
        ChatContent answer = chatContentMapper.toEntity(chatCompletionResult, chat);
        chatContentRepository.saveAll(List.of(question, answer));

        return chatContentMapper.fromEntity(answer);
    }

    @Override
    public GptResponse chatSelect(Long articleId, String type) {

        Article article = articleRepository.findByIdAndIsEnable(articleId, true)
                .orElseThrow(ArticleNotFoundException::new);
        SelectType selectType = SelectType.getSelectTypeByName(type);

        if (!chatSelectRepository.existsByArticleAndSelectType(article, selectType)) {
            ChatCompletionRequest chatCompletionRequest = chatCompletionMapper.fromEntity(article, selectType);
            ChatCompletionResult chatCompletionResult = openAiService.createChatCompletion(chatCompletionRequest);

            chatSelectRepository.save(chatSelectMapper.toEntity(chatCompletionResult, article, selectType));
        }

        ChatSelect chatSelect = chatSelectRepository.findByArticleAndSelectType(article, selectType)
                .orElseThrow(ChatSelectNotFoundException::new);

        return chatSelectMapper.fromEntity(chatSelect);
    }
}
