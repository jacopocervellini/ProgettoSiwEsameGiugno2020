package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Project {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;


	@Column(nullable=false,unique=true)
	private String name;

	@Column
	private String description;


	@ManyToOne(fetch=FetchType.LAZY)
	private User owner;

	@ManyToMany(mappedBy="visibleProjects",fetch=FetchType.LAZY)
	private List<User> members;

	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="project_id")
	private List<Task> ownertask;

	@OneToMany(cascade = CascadeType.REMOVE,
			fetch = FetchType.LAZY)
	private List<Tag> projectTags;
	
	public Project() {
		this.members=new ArrayList<User>();
		this.ownertask= new ArrayList<Task>();
		this.projectTags= new ArrayList<Tag>();
	}
	
	public Project(String name, String description) {
		this();
		this.name=name;
		this.description=description;
		this.projectTags= new ArrayList<Tag>();
	}
	
	public void addMember(User u) {
		this.members.add(u);
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<User> getUsers() {
		return members;
	}

	public void setUsers(List<User> users) {
		this.members= users;
	}

	public List<Task> getOwnertask() {
		return ownertask;
	}

	public void setOwnertask(List<Task> ownertask) {
		this.ownertask = ownertask;
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

	public List<User> getMembers() {
		return this.members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	public List<Tag> getProjectTags() { 
		return this.projectTags; 
		}
	
	public void setProjectTags(List<Tag> projectTags) { 
		this.projectTags = projectTags; 
		}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Project other = (Project) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	


}
