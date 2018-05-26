package com.articles.springboot.controller;

import java.util.List;

import com.articles.springboot.model.Article;
import com.articles.springboot.service.ArticleService;
import com.articles.springboot.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	ArticleService articleService;

	//Get all articles
	@RequestMapping(value = "/article/", method = RequestMethod.GET)
	public ResponseEntity<List<Article>> getAllArticles(){
		List<Article> articles = articleService.findAllArticles();
		if (articles == null || articles.isEmpty()) {
			logger.warn("Article list is empty");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
	}

	//Create new article
	@RequestMapping(value = "/article/", method = RequestMethod.POST)
	public ResponseEntity<Article> addArticle(@RequestBody Article article){
		if (article == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		if (articleService.isArticleExist(article)){
			logger.error("Unable to create. An article with header {} already exist", article.getHeader());
			return new ResponseEntity(new CustomErrorType("Unable to create. A User with header " +
					article.getHeader() + " already exist."),HttpStatus.CONFLICT);
		}

		articleService.createArticle(article);
		return new ResponseEntity<>(article, HttpStatus.OK);
	}

	//Update an existing article
	@RequestMapping(value = "/article/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody Article article){
		logger.info("Update article with id {}", id);

		Article currentArticle = articleService.findById(id);

		if (currentArticle == null){
			logger.error("Unable to update. Article with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to update. Article with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentArticle.setText(article.getText());
		currentArticle.setAuthors(article.getAuthors());
		currentArticle.setDescription(article.getDescription());
		currentArticle.setHeader(article.getHeader());
		currentArticle.setKeywords(article.getKeywords());
		currentArticle.setPublishDate(article.getPublishDate());

		articleService.updateArticle(currentArticle);
		return new ResponseEntity<Article>(currentArticle, HttpStatus.OK);
	}

	//Delete an article
	@RequestMapping(value = "/article/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteArticle(@PathVariable("id") long id){
		logger.info("Fetching & Deleting Article with id {}", id);

		Article article = articleService.findById(id);
		if (article == null) {
			logger.error("Unable to delete. Article with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Article with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		articleService.deleteArticleById(id);
		return new ResponseEntity<Article>(HttpStatus.OK);
	}

	// Get a single article
	@RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getArticle(@PathVariable("id") long id) {
		logger.info("Fetching Article with id {}", id);
		Article article = articleService.findById(id);
		if (article == null){
			logger.error("Article with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Article with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Article>(article, HttpStatus.OK);
	}

	//get articles for a given author
	@RequestMapping(value = "/article/getByAuthor/{authorName}", method = RequestMethod.GET)
	public ResponseEntity<?> getArticlesByAuthor(@PathVariable("authorName") String authorName ){
		logger.info("Getting all articles for author = {}", authorName);
		List<Article> articles = articleService.getArticlesByAuthor(authorName);
		if (articles == null || articles.isEmpty()){
			logger.error("Could not find articles for the author {authorName}", authorName);
			return new ResponseEntity(new CustomErrorType("Could not found articles for author = " + authorName)
					, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
	}

	//get articles for a given keyword
	@RequestMapping(value = "/article/getByKeyword/{keyword}", method = RequestMethod.GET)
	public ResponseEntity<?> getArticlesByKeyword(@PathVariable("keyword") String keyword ){
		logger.info("Getting all articles for keyword = {}", keyword);
		List<Article> articles = articleService.getArticlesByKeyword(keyword);
		if (articles == null || articles.isEmpty()){
			logger.error("Could not find articles for the author {keyword}", keyword);
			return new ResponseEntity(new CustomErrorType("Could not found articles for keyword = " + keyword)
					, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
	}

	//get articles for a given period (Date format must be dd-mm-yyyy)
	@RequestMapping(value = "/article/getByPeriod/{startDate}/{endDate}", method = RequestMethod.GET)
	public ResponseEntity<?> getArticlesByPeriod(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate ){
		logger.info("Getting all articles for period = {}-{}", startDate, endDate);
		List<Article> articles = articleService.getArticlesByPeriod(startDate, endDate);
		if (articles == null || articles.isEmpty()){
			logger.error("Could not find articles for the period {}-{}", startDate, endDate);
			return new ResponseEntity(new CustomErrorType("Could not found articles for period = " + startDate + "-" + endDate)
					, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
	}
}
