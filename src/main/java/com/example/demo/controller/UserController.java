package com.example.demo.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Util.JwtUtil;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.JwtRequest;
import com.example.demo.entity.JwtResponse;
import com.example.demo.entity.User;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private JwtRequest jwtRequest;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostConstruct
	public void initRolesAndUsers() {
		userService.initRolesAndUser();
		
	}
	
	@PostMapping({"/registerNewUser"})
	public JwtResponse registerNewUser(@RequestBody User user) throws Exception {
		jwtRequest.setUserPassword(user.getUserPassword());
		jwtRequest.setUserName(user.getUserName());
		
//		User userDetails =  userService.registerNewUser(user);
		return jwtService.createJwtToken(jwtRequest);
	}
	
	@GetMapping({"/forAdmin"})
	@PreAuthorize("hasRole('ADMIN')")
	public String forAdmin() {
		return "This URL is only accessible to admin.";
	}
	
	@GetMapping({"/forUser"})
	@PreAuthorize("hasRole('USER')")
	public String forUser() {
		return "This URL is only accessible to user.";
	}
	
	@GetMapping({"/getUserDetails"})
	public User userDetails() {
		String token = jwtService.token;
		String userName = jwtUtil.getSubjectFromToken(token);
		return userDao.findUserDetailUsingUserName(userName);  
		
	}
	
}
