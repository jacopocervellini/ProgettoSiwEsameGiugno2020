package com.example.demo.repository;


import java.util.Optional;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Comment;


@Repository
public interface CommentRepository extends CrudRepository<Comment,Long>{
	
	Optional<Comment> findById(Long id);

}
