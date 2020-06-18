package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.UserCredentials;

public interface UserCredentialsRepository extends CrudRepository<UserCredentials,Long>{
	
	public Optional<UserCredentials> findByUsername(String username);

}
