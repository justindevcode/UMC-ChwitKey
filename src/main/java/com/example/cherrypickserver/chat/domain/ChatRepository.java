package com.example.cherrypickserver.chat.domain;

import com.example.cherrypickserver.article.domain.Article;
import com.example.cherrypickserver.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByMemberAndArticle(Member member, Article article);
}
