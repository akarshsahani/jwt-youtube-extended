package com.example.demo.entity;

import org.springframework.stereotype.Service;

@Service
public class JwtRequest {
	
//	@Autowired
//	private UserDao userDao;
//	
	
	private String userName;
	private String userPassword;
	
//	private long userId = userDao.
	
	
	
	
//	public long getUserId() {
//		return userId;
//	}
//	public void setUserId(long userId) {
//		this.userId = userId;
//	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		
		
		this.userName = userName;
		
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
		
	}
	
	

}
