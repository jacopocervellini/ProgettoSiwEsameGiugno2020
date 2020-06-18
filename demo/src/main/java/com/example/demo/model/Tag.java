package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;



@Entity
public class Tag {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column
	private String color;

	@Column
	private String name;

	@Column
	private String description;

	@ManyToMany(cascade = CascadeType.REMOVE)
	private List<Task> ownerTask;


	public Tag() {
		this.ownerTask = new ArrayList<Task>();

	}

	public Tag(String nome,String descrizione,String colore) {
		this();
		this.color=colore;
		this.description=descrizione;
		this.name=nome;
	}

	public String getColor() {
		return color; 
		}
	
	public String getDescription() { 
		return description; 
		}
	
	public String getName() { 
		return name; 
		}

	public void setColor(String colore){
		this.color = colore;
	}

	public void setDescription(String description) { 
		this.description = description; 
	}
	public void setName(String name) { 
		this.name = name;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Task> getOwnerTask() {
		return ownerTask;
	}

	public void setOwnerTask(List<Task> ownerTask) {
		this.ownerTask = ownerTask;
	}

	@Override
	public boolean equals(Object obj) {
		Tag that = (Tag) obj;
		return this.name.equals(that.getName()) && this.color.equals(that.getColor());
	}

	@Override
	public int hashCode() {
		return this.name.hashCode() + this.color.hashCode();
	}

	//TODO
	@Override
	public String toString() {
		return "";
	}

}



