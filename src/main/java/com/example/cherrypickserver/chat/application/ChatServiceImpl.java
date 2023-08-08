package com.example.cherrypickserver.chat.application;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.article.domain.ArticleRepository;
import com.example.cherrypickserver.article.exception.ArticleNotFoundException;
import com.example.cherrypickserver.chat.domain.*;
import com.example.cherrypickserver.chat.dto.response.GptResponse;
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
    public ChatResponse createChatAndContent(Long memberId, Long articleId) {

        Member member = memberRepository.findByIdAndIsEnable(memberId, true)
                .orElseThrow(MemberNotFoundException::new);
        Article article = articleRepository.findByIdAndIsEnable(articleId, true)
                .orElseThrow(ArticleNotFoundException::new);

        Chat chat = chatRepository.save(chatMapper.toEntity(member, article));

        //시스템 설정용 chatContent
        String content = "당신은 '취트키'라는 어플에서 " + article.getIndustry().getValueGpt()
                + " 분야 기사 질문에 대답하는 시스템입니다. "
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
        List<ChatContent> chatContentList = chatContentRepository.findAllByChat(chat, Sort.by("createdAt"));

        //Chat GPT에 넘겨줄 이전 대화 목록
        List<ChatMessage> chatMessageList = chatContentList.stream()
                .map(chatContent -> new ChatMessage(chatContent.getRole(), chatContent.getContent()))
                .collect(Collectors.toList());

        chatMessageList.add(new ChatMessage("user", questionRequest.getQuestion()));

//        System.out.println("chatMessageList = " + chatMessageList);

        ChatCompletionRequest chatCompletionRequest = chatCompletionMapper.fromEntity(chatMessageList);
        ChatCompletionResult chatCompletionResult = openAiService.createChatCompletion(chatCompletionRequest);

        //대화 내용 저장
        ChatContent question = chatContentMapper.toEntity("user", questionRequest.getQuestion(), chat);
        ChatContent answer = chatContentMapper.toEntity(chatCompletionResult, chat);
        chatContentRepository.saveAll(List.of(question, answer));

//        GptFullResponse gptFullResponse = GptFullResponse.of(chatCompletionResult);
//        System.out.println("gptFullResponse PromptTokens = " + gptFullResponse.getUsage().getPromptTokens());
//        System.out.println("gptFullResponse CompletionTokens = " + gptFullResponse.getUsage().getCompletionTokens());
//        System.out.println("gptFullResponse TotalTokens = " + gptFullResponse.getUsage().getTotalTokens());

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
