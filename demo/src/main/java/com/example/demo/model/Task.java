package com.example.demo.model;

import java.time.LocalDateTime;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;



@Entity
public class Task {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(nullable=false)
	private String name;

	@Column
	private String description;


	@Column(nullable=false)
	private boolean completed;

	@Column
	private LocalDateTime creationTimeStamp;

	@Column
	private LocalDateTime lastUpgradeTimeStamp;

	@ManyToMany(mappedBy="ownerTask",cascade = CascadeType.REMOVE,
			fetch = FetchType.LAZY)
	private List<Tag> taskTags;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Project project;
	
	@OneToMany
	private List<Comment> commentTask;

	
	public Task() {
		this.taskTags = new ArrayList<Tag>();
		this.commentTask= new ArrayList<Comment>();
		
	}

	public Task(String name, String description, boolean completed) {
		this();
		this.name=name;
		this.description=description;
		this.completed=completed;
	}

	
	
	public List<Comment> getCommentTask() {
		return commentTask;
	}

	public void setCommentTask(List<Comment> commentTask) {
		this.commentTask = commentTask;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public LocalDateTime getCreationTimeStamp() {
		return creationTimeStamp;
	}


	public void setCreationTimeStamp(LocalDateTime creationTimeStamp) {
		this.creationTimeStamp = creationTimeStamp;
	}


	public LocalDateTime getLastUpgradeTimeStamp() {
		return lastUpgradeTimeStamp;
	}


	public void setLastUpgradeTimeStamp(LocalDateTime lastUpgradeTimeStamp) {
		this.lastUpgradeTimeStamp = lastUpgradeTimeStamp;
	}


	@PrePersist
	protected void onPersist() {
		this.creationTimeStamp= LocalDateTime.now();
		this.lastUpgradeTimeStamp= LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate(){
		this.lastUpgradeTimeStamp= LocalDateTime.now();
	}


	public List<Tag> getTaskTags() {
		return taskTags;
	}

	public void setTaskTags(List<Tag> taskTags) {
		this.taskTags = taskTags;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", description=" + description + ", creationTimeStamp="
				+ creationTimeStamp + ", lastUpgradeTimeStamp=" + lastUpgradeTimeStamp + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (completed ? 1231 : 1237);
		result = prime * result + ((creationTimeStamp == null) ? 0 : creationTimeStamp.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((lastUpgradeTimeStamp == null) ? 0 : lastUpgradeTimeStamp.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (completed != other.completed)
			return false;
		if (creationTimeStamp == null) {
			if (other.creationTimeStamp != null)
				return false;
		} else if (!creationTimeStamp.equals(other.creationTimeStamp))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (lastUpgradeTimeStamp == null) {
			if (other.lastUpgradeTimeStamp != null)
				return false;
		} else if (!lastUpgradeTimeStamp.equals(other.lastUpgradeTimeStamp))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}



}
