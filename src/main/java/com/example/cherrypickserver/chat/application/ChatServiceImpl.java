package com.example.cherrypickserver.chat.application;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.article.domain.ArticleRepository;
import com.example.cherrypickserver.chat.domain.Chat;
import com.example.cherrypickserver.chat.domain.ChatContent;
import com.example.cherrypickserver.chat.domain.ChatContentRepository;
import com.example.cherrypickserver.chat.domain.ChatRepository;
import com.example.cherrypickserver.chat.dto.GptFullResponse;
import com.example.cherrypickserver.chat.dto.GptRequest;
import com.example.cherrypickserver.chat.dto.GptResponse;
import com.example.cherrypickserver.chat.mapper.ChatCompletionMapper;
import com.example.cherrypickserver.chat.mapper.ChatContentMapper;
import com.example.cherrypickserver.chat.mapper.ChatMapper;
import com.example.cherrypickserver.member.domain.Member;
import com.example.cherrypickserver.member.domain.MemberRepository;
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

    private final ChatMapper chatMapper;
    private final ChatContentMapper chatContentMapper;
    private final ChatCompletionMapper chatCompletionMapper;

    private final OpenAiService openAiService;

    @Override
    public GptResponse chatCompletion(GptRequest gptRequest) {

        Member member = memberRepository.findById(gptRequest.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException()); //추후 예외처리 수정
        Article article = articleRepository.findById(gptRequest.getArticleId())
                .orElseThrow(() -> new IllegalArgumentException()); //추후 예외처리 수정

        Chat chat = chatRepository.findByMemberAndArticle(member, article)
                .orElseGet(() -> createChatAndContent(member, article));
        List<ChatContent> chatContentList = chatContentRepository.findAllByChat(chat, Sort.by("createdAt"));

        //Chat GPT에 넘겨줄 이전 대화 목록
        List<ChatMessage> chatMessageList = chatContentList.stream()
                .map(chatContent -> new ChatMessage(chatContent.getRole(), chatContent.getContent()))
                .collect(Collectors.toList());

        chatMessageList.add(new ChatMessage("user", gptRequest.getQuestion() + " 10초 이내로 답변해줘"));

//        System.out.println("chatMessageList = " + chatMessageList);

        ChatCompletionRequest chatCompletionRequest = chatCompletionMapper.fromEntity(chatMessageList);
        ChatCompletionResult chatCompletionResult = openAiService.createChatCompletion(chatCompletionRequest);

        //대화 내용 저장
        ChatContent question = chatContentMapper.toEntity("user", gptRequest.getQuestion(), chat);
        ChatContent answer = chatContentMapper.toEntity(chatCompletionResult, chat);
        chatContentRepository.saveAll(List.of(question, answer));

//        GptFullResponse gptFullResponse = GptFullResponse.of(chatCompletionResult);
//        System.out.println("gptFullResponse PromptTokens = " + gptFullResponse.getUsage().getPromptTokens());
//        System.out.println("gptFullResponse CompletionTokens = " + gptFullResponse.getUsage().getCompletionTokens());
//        System.out.println("gptFullResponse TotalTokens = " + gptFullResponse.getUsage().getTotalTokens());

        GptResponse gptResponse = chatContentMapper.fromEntity(answer);

        return gptResponse;
    }

    public Chat createChatAndContent(Member member, Article article) {

        Chat chat = chatMapper.toEntity(member, article);
        chatRepository.save(chat);

        //시스템 설정용 content - 추후 직군, 기사 내용 포함
        String content = "당신은 IT 분야 기사에 대한 질문에 대답하는 시스템입니다. " +
                "10초 이내로 답변하고, 분야와 관련되지 않는 질문에는 답변하지 마세요.";

        ChatContent chatContent = chatContentMapper.toEntity("system", content, chat);
        chatContentRepository.save(chatContent);

        return chat;
    }
}
