package com.example.demo.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	private static final String SECRET_KEY = "learn_programming_yourself";
	private static final int TOKEN_VALIDITY = 3600 * 5;

	public String getSubjectFromToken(String token) {
      return getClaimFromToken(token, Claims::getSubject);
	}
	
	private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
		final Claims claims = getAllClaimsFromToken(token);
//		System.out.println("claims class response "+claims);
//		String user = (String) claims.get("userPassword");
//		System.out.println(user);
//		T res =  claimResolver.apply(claims);
//		System.out.println("resolve: "+res);
		return claimResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken (String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		String userName = getSubjectFromToken(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		Date expirationDate = getExpirationDateFromToken(token);
		return expirationDate.before(new Date());
	}
	
	private Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
//	public String generateToken(UserDetails userDetails) {
//		Map<String, Object> claims = new HashMap<>();
//		claims.put("userName", userDetails.getUsername());
//		claims.put("userPassword", userDetails.getPassword());
//		claims.put("Role", userDetails.getAuthorities());
//		return Jwts.builder()
//				.setClaims(claims)
//				.setSubject(userDetails.getUsername())
//				.setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
//				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
//				.compact()
//				;
//	}
	
	public String generateToken(Map<String, Object> claims, String sub) {
		String sub2 = sub != null ? sub : "";
//		Map<String, Object> claims = new HashMap<>();
//		claims.put("userName", userDetails.getUsername());
//		claims.put("userPassword", userDetails.getPassword());
//		claims.put("Role", userDetails.getAuthorities());
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(sub)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact()
				;
	}
	
//	public String generateTokenForNewUser(Map<String, Object> claims, String sub) {
//	    String sub2 = sub != null ? sub : ""; 
////		Map<String, Object> claims = new HashMap<>();
////		claims.put("userName", usr.getUserName());
////		claims.put("userPassword", usr.getUserPassword());
//		//claims.put("Role", usr.getAuthorities());
//		return Jwts.builder()
//				.setClaims(claims)
//				.setSubject(sub2)
//				.setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
//				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
//				.compact()
//				;
//	}
}
