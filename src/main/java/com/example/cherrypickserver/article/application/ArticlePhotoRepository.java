package com.example.cherrypickserver.article.application;

import com.example.cherrypickserver.article.domain.ArticlePhoto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticlePhotoRepository extends JpaRepository<ArticlePhoto, Long>
{

}
