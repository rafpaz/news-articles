package com.articles.springboot.service;

import com.articles.springboot.model.Post;
import com.articles.springboot.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("postService")
@Transactional
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Override
	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}

	@Override
	public List<Post> getUserPosts(Integer userId) {
		return postRepository.findByUserId(userId);
	}

	@Override
	public void addPost(Post post) {
		postRepository.save(post);
	}
}
