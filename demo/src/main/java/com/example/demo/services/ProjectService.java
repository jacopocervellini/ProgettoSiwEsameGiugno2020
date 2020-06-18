package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Transactional
	public Project getProject (Long id) {
		Optional<Project> result= this.projectRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
	public Project saveProject(Project p) {
		return this.projectRepository.save(p);
	}
	
	@Transactional
	public void deleteProject(Project p) {
		this.projectRepository.delete(p);
	}
	
	@Transactional
	public Project shareProjectWithUser(Project p, User u) {
		p.addMember(u);
		u.getVisibleProjects().add(p);
		this.userRepository.save(u);
		return this.projectRepository.save(p);
	}

}
