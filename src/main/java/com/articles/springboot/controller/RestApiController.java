package com.articles.springboot.controller;

import java.util.List;

import com.articles.springboot.model.Article;
import com.articles.springboot.model.Post;
import com.articles.springboot.model.User;
import com.articles.springboot.service.PostService;
import com.articles.springboot.service.UserService;
import com.articles.springboot.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	UserService userService;

	@Autowired
	PostService postService;

	//Get all users
	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAllUsers();
		if (users.isEmpty())
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	// Get a single user
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		logger.info("Fetching User with id {}", id);
		User user = userService.findById(id);
		if (user == null){
			logger.error("User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("User with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	//Create a new user
	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder){
		logger.info("Creating User: {}", user);

		if (userService.isUserExist(user)){
			logger.error("Unable to create. A User with name {} already exist", user.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " +
					user.getName() + " already exist."),HttpStatus.CONFLICT);
		}

		userService.saveUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	//update a user
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user){
		logger.info("Update User with id {}", id);

		User currentUser = userService.findById(id);

		if (currentUser == null){
			logger.error("Unable to update. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentUser.setName(user.getName());
		currentUser.setAge(user.getAge());
		currentUser.setSalary(user.getSalary());

		userService.updateUser(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}

	//Delete a user
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id){
		logger.info("Fetching & Deleting User with id {}", id);

		User user = userService.findById(id);
		if (user == null) {
			logger.error("Unable to delete. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		userService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	//Delete all users
	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers(){
		logger.info("Deleting All Users");

		userService.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	//user login
	@RequestMapping(value = "/user/{name}/{pass}", method = RequestMethod.GET)
	public ResponseEntity<User> userLogin(@PathVariable("name") String name, @PathVariable("pass") String pass){
		logger.info("looking for a user with name {}", name);
		User user = userService.findByName(name);
		if (user == null) {
			logger.error("User with name {} not found", name);
			return new ResponseEntity(new CustomErrorType("User with name " + name + " not found"), HttpStatus.NOT_FOUND);
		}
		if (!user.getPassword().equals(pass)){
			logger.error("User found, but password is incorrect");
			return new ResponseEntity(new CustomErrorType("Incorrect password: " + pass), HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/post/", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> getPosts(){
		List<Post> posts = postService.getAllPosts();
		if (posts.isEmpty())
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}

	@RequestMapping(value = "/post/", method = RequestMethod.POST)
	public ResponseEntity<?> createPost(@RequestBody Post post){
		postService.addPost(post);
		return new ResponseEntity<Post>(post, HttpStatus.OK);
	}

	@RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> getUserPosts(@PathVariable("id") String id){
		List<Post> posts = postService.getUserPosts(Integer.parseInt(id));
		if (posts.isEmpty())
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}

	@RequestMapping(value = "/article/", method = RequestMethod.POST)
	public ResponseEntity<Article> addArticle(@RequestBody Article article){

		return new ResponseEntity<>(article, HttpStatus.OK);
	}

	@RequestMapping(value = "/article/", method = RequestMethod.GET)
	public ResponseEntity<Article> getArticles(){

	}
}
