package com.example.demo.services;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.model.UserCredentials;
import com.example.demo.repository.UserCredentialsRepository;



@Service
public class UserCredentialsService {

	@Autowired
	private UserCredentialsRepository credentialRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;


	public UserCredentials getCredentials(String username) {
		Optional<UserCredentials> result =this.credentialRepository.findByUsername(username);
		return result.orElse(null);
	}

	@Transactional
	public UserCredentials getCredentials(Long id) {
		Optional<UserCredentials> result =this.credentialRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public UserCredentials saveCredentials(UserCredentials credentials) {
		credentials.setRole(UserCredentials.DEFAULT_ROLE);
		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		return this.credentialRepository.save(credentials);
	}

	@Transactional
	public List<UserCredentials> getAllCredentials() {
		Iterable<UserCredentials> credentials=this.credentialRepository.findAll();
		List<UserCredentials> result = new ArrayList<>();
		for(UserCredentials u: credentials) {
			result.add(u);}
		return result;
	}

	@Transactional
	public void deleteCredentials(String username) {
		UserCredentials credentials=this.getCredentials(username);
		this.credentialRepository.delete(credentials);
	}

}
