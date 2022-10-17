package com.example.demo.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Util.JwtUtil;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.JwtRequest;
import com.example.demo.entity.JwtResponse;
import com.example.demo.entity.User;

@Service
public class JwtService implements UserDetailsService{
	
	public String token;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
		String userName = jwtRequest.getUserName();
//		System.out.println("create token user name: " + userName);
		String userPassword = jwtRequest.getUserPassword();
//		Set role = new HashSet();
//		role
//		role = getAuthorities();
		authunticate(userName, userPassword);
		
//		final UserDetails userDetails = loadUserByUsername(userName);
		UserDetails userDetails = loadUserByUsername(userName);
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("userName", userDetails.getUsername());
		claims.put("userPassword", userDetails.getPassword());
		claims.put("Role", userDetails.getAuthorities());
		String sub = userDetails.getUsername();
		
		String newGeneratedToken = jwtUtil.generateToken(claims, sub);
		token = newGeneratedToken;
		User user = userDao.findUserDetailUsingUserName(userName);
		
		return new JwtResponse(user, newGeneratedToken);
	}
	
//	public String createJwtTokenForNewUser(User usr) throws Exception {
//		String userName = usr.getUserName();
//		String userPassword = usr.getUserPassword();
////		Set role = new HashSet();
////		role
////		role = getAuthorities() 
//		//authunticate(userName, userPassword);
//		
////		final UserDetails userDetails = loadUserByUsername(userName);
//		//UserDetails userDetails = loadUserByUsername(userName);
//		Map <String, Object> claims = new HashMap();
//		claims.put("userName", usr.getUserName());
//		claims.put("userPassword", usr.getUserPassword());
//		claims.put("Role", getAuthorities(usr));
//		String sub = usr.getUserName();
//		
//		String newGeneratedToken = jwtUtil.generateTokenForNewUser(claims, sub);
//		User user = userDao.findById(userName).get();
//		
//		return newGeneratedToken;
//	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user =  userDao.findUserDetailUsingUserName(username);
		
		if(user != null) {
//			System.out.println("username : " + user.getUserName());
//			System.out.println("UserPassword : " + user.getUserPassword());
//			System.out.println("userRole : " + getAuthorities(user));
			return new org.springframework.security.core.userdetails.User(
					user.getUserName(),
					user.getUserPassword(),
					getAuthorities(user));
		}else {
			throw new UsernameNotFoundException("Username is not valid");
			
		}
	}
	
	private Set<SimpleGrantedAuthority> getAuthorities(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
		//System.out.println(user);
		user.getRole().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
		});
		//System.out.println("authorities : " + authorities);
		return authorities;
	}
	
	private void authunticate(String userName, String userPassword) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
		}catch(DisabledException e) {
			throw new Exception("User is disabled");
		}catch(BadCredentialsException e) {
			throw new Exception("Bad credentials from user");
		}
	}


}
