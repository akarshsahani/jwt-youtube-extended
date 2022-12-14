package com.example.demo.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	@Column(unique=true)
	private String userName;
	private String userFirstName;
	private String userLastName;
	private String userPassword;
	
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinTable(name = "USER_ROLE",
	joinColumns = {
			@JoinColumn(name = "USER_ID", referencedColumnName = "userId"),
			//@JoinColumn(name = "USER_NAME", referencedColumnName = "userName")
	},
	inverseJoinColumns = {
			@JoinColumn(name = "ROLE_ID", referencedColumnName = "roleId"),
//			@JoinColumn(name = "ROLE_NAME", referencedColumnName = "roleName")
	})
	private Set<Role> role;

	

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) { 
		this.userName = userName;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [userId=" +  ", userName=" + userName + ", userFirstName=" + userFirstName
				+ ", userLastName=" + userLastName + ", userPassword=" + userPassword + ", role=" + role + "]";
	}

	
	
	
	
}
