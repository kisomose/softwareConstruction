package com.example.employeemanagementwebapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String Rolename;
	
	
	public Role() {
		
	}
	
	public Role(String Rolename) {
		super();
		this.Rolename = Rolename;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getRolename() {
		return Rolename;
	}

	public void setRolename(String rolename) {
		Rolename = rolename;
	} 
	}