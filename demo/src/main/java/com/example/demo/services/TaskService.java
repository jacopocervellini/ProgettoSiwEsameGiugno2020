package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Transactional
	public Task getTask (Long id) {
		Optional<Task> result = this.taskRepository.findById(id);
		return result.orElse(null); 
	}

	@Transactional
	public void deleteTask(Task t) {
		this.taskRepository.delete(t);
	}
	
	@Transactional
	public Task saveTask(Task t) {
		return this.taskRepository.save(t);
	}

	@Transactional
	public Task setCompleted(Task t) {
		t.setCompleted(true);
		return this.taskRepository.save(t);
	}
	
	

}
