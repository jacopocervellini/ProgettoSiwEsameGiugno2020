package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;


@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	//@Column(nullable = false, length=252)
	private String text;

	//@Column(nullable = false, updatable=false)
	private LocalDateTime creationTime;
	
	@ManyToOne()
	private Task task;
	

	public Comment() {}
	
	public Comment(String newCommentText) {
		this.text = newCommentText;
	}

	@PrePersist
	protected void onPersist() {
		this.creationTime = LocalDateTime.now();
	}
	

	
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public Long getId() {
		return id;
	}


	public String getText() {
		return text;
	}
	
	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	
	@Override
	public boolean equals(Object obj) {
		Comment that = (Comment) obj;
		return  this.text.equals(that.getText());
	}
	
	@Override
	public int hashCode() {
		return  this.text.hashCode();
	}
	

}
