package com.example.cherrypickserver.chat.domain;

import com.example.cherrypickserver.global.entity.BaseEntity;

import javax.persistence.*;

public class ChatContent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_content_id")
    private Long id;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chat_id")
    private Chat chat;
}
