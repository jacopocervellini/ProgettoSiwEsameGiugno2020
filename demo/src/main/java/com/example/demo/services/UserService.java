package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public User getUser (Long id) {
		Optional<User> result= this.userRepository.findById(id);
		return result.orElse(null);
	}


	@Transactional
	public User saveUser (User user) {
		return this.userRepository.save(user);
	}

	@Transactional
	public List<User> findAllUser(){
		List<User> lista= new ArrayList<>();
		Iterable<User> result = this.findAllUser();
		for(User u: result)
			lista.add(u);
		return lista;
	}

	@Transactional
	public List<User> getMembers(Project p){
		List<User> lista= new ArrayList<>();
		Iterable<User> result = this.userRepository.findByVisibleProjects(p);
		for(User u: result)
			lista.add(u);
		return lista;
	}

	
	
}
