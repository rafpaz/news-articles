package com.articles.springboot.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.articles.springboot.model.Article;
import com.articles.springboot.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService{

	@Autowired
	private ArticleRepository articleRepository;

	@Override
	public Article findById(Long id) {
		return articleRepository.findOne(id);
	}

	@Override
	public Article findByHeader(String header) {
		return articleRepository.findByHeader(header);
	}

	@Override
	public void createArticle(Article user) {
		articleRepository.save(user);
	}

	@Override
	public void updateArticle(Article user) {
		createArticle(user);
	}

	@Override
	public void deleteArticleById(Long id) {
		articleRepository.delete(id);
	}

	@Override
	public List<Article> findAllArticles() {
		return articleRepository.findAll();
	}

	@Override
	public boolean isArticleExist(Article article) {
		return findByHeader(article.getHeader()) != null;
	}

	@Override
	public List<Article> getArticlesByAuthor(String author) {
		List<Article> articles = findAllArticles();
		List<Article> filtered = articles.stream().filter(a -> (a.getAuthors().contains(author))).collect(Collectors.toList());
		return filtered;
	}

	@Override
	public List<Article> getArticlesByKeyword(String keyword) {
		List<Article> articles = findAllArticles();
		return articles.stream().filter(a -> (a.getKeywords().contains(keyword))).collect(Collectors.toList());
	}

	@Override
	public List<Article> getArticlesByPeriod(String start, String end) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date startDate, endDate;
		try {
			startDate = formatter.parse(start);
			endDate = formatter.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		List<Article> articles = findAllArticles();
		return articles.stream().filter(a -> (a.getPublishDate().compareTo(startDate) >= 0 &&
												a.getPublishDate().compareTo(endDate) <= 0)).collect(Collectors.toList());
	}
}
