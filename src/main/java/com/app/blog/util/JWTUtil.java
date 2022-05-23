package com.app.blog.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	//create secret
	
	@Value("${app.secret}")
	private String secret;
	
	
	public boolean validateUser(String token,String username) {
		String token_username=getUsername(token);
		
		return(username.equals(token_username) && !isTokenExp(token));
	}
	
	public boolean isTokenExp(String token) {
		Date expdate=getExpiryDate(token);
		return expdate.before(new Date(System.currentTimeMillis()));
	}
	
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}
	
	public Date getExpiryDate(String token) {
		return getClaims(token).getExpiration();
	}
	
	public Claims getClaims(String token) {
		return Jwts.parser()
					.setSigningKey(secret.getBytes())
					.parseClaimsJws(token)
					.getBody();
	}
	
	public String GenerateToken(String subject) {
		
		String token= Jwts.builder()
						.setSubject(subject)
						.setIssuer("admin")
						.setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(30)))
						.signWith(SignatureAlgorithm.HS512,secret.getBytes())
						.compact()
						;
		
		return token;
		
		
	}
	
}
