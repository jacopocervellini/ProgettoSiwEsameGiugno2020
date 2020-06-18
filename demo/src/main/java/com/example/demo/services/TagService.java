package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Tag;

import com.example.demo.repository.TagRepository;


@Component
public class TagService {
	
	@Autowired
	private TagRepository tagRepository;

	@Transactional
	public Tag getTag (Long id) {
		Optional<Tag> result = this.tagRepository.findById(id);
		return result.orElse(null); 
	}

	@Transactional
	public void deleteTag(Tag t) {
		this.tagRepository.delete(t);
	}
	
	@Transactional
	public Tag saveTag(Tag t) {
		return this.tagRepository.save(t);
	}



}
