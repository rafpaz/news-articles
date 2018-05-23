package com.articles.springboot.service;

import java.util.List;

import com.articles.springboot.model.Article;
import com.articles.springboot.repositories.UserRepository;
import com.articles.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService{

	@Override
	public Article findById(Long id) {
		return null;
	}

	@Override
	public Article findByName(String name) {
		return null;
	}

	@Override
	public void saveArticle(Article article) {

	}

	@Override
	public void updateArticle(User user) {

	}

	@Override
	public void deleteArticleById(Long id) {

	}

	@Override
	public void deleteAllArticles() {

	}

	@Override
	public List<User> findAllArticles() {
		return null;
	}

	@Override
	public boolean isArticleExist(Article article) {
		return false;
	}
}
