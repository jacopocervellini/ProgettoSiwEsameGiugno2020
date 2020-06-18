package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Tag;


public interface TagRepository extends CrudRepository<Tag,Long>{

	
	Optional<Tag> findById(Long id);
}
