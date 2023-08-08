package com.example.cherrypickserver.chat.domain;

import com.example.cherrypickserver.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ChatContent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_content_id")
    private Long id;

    @Column(nullable = false)
    private String role;

    @Column(columnDefinition="TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Builder
    public ChatContent(String role, String content, Chat chat) {
        this.role = role;
        this.content = content;
        this.chat = chat;
    }

    public void delete() {
        this.setIsEnable(false);
    }
}
