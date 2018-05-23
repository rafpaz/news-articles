package com.articles.springboot.service;

import com.articles.springboot.model.Article;
import com.articles.springboot.model.User;

import java.util.List;

public interface ArticleService {
	Article findById(Long id);

	Article findByName(String name);

	void saveArticle(Article article);

	void updateArticle(User user);

	void deleteArticleById(Long id);

	void deleteAllArticles();

	List<User> findAllArticles();

	boolean isArticleExist(Article article);
}
