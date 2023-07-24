package com.example.cherrypickserver.chat.domain;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatContentRepository extends JpaRepository<ChatContent, Long> {

    List<ChatContent> findAllByChat(Chat chat, Sort sort);
}
