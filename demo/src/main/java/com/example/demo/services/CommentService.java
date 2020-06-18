package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Comment;
import com.example.demo.repository.CommentRepository;


@Component
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Transactional
	public Comment getComment(Long id) {
		Optional<Comment> result = this.commentRepository.findById(id);
		return result.orElse(null); 
	}

	@Transactional
	public void deleteComment(Comment c) {
		this.commentRepository.delete(c);
	}
	
	@Transactional
	public Comment saveComment(Comment c) {
		return this.commentRepository.save(c);
	}

}
