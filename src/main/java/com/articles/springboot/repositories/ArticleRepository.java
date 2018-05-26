package com.articles.springboot.repositories;

import com.articles.springboot.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	List<Article> findByKeywords(String keyword);

	List<Article> findByPublishDateBetween(Date start, Date end);

	Article findByHeader(String header);

//	ArrayList<Article> getArticlesByAuthorsContains(String name);

//	List<Article> find(@Param("authorName") String authorName);
}
