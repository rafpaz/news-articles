package com.articles.springboot.service;

import com.articles.springboot.model.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface ArticleService {
	Article findById(Long id);

	Article findByHeader(String header);

	void createArticle(Article article);

	void updateArticle(Article article);

	void deleteArticleById(Long id);

	List<Article> findAllArticles();

	boolean isArticleExist(Article article);

	List<Article> getArticlesByAuthor(String name);

	List<Article> getArticlesByKeyword(String name);

	List<Article> getArticlesByPeriod(String startDate, String endDate);
}
