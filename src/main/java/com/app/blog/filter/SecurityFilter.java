package com.app.blog.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.blog.util.JWTUtil;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	private JWTUtil util;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {

		//1.read the token
		
		final String token = request.getHeader("Authorization");
		if(token!=null) {
			//do validation
			
			String username= util.getUsername(token);
			
			//usename shouln't empty and context-auth must be empty
			if(username!=null && SecurityContextHolder.getContext().getAuthentication() ==null) {
				
				UserDetails usr = userDetailsService.loadUserByUsername(username);
				
				//validate token
				
				boolean isValid=util.validateUser(token, usr.getUsername());
				if(isValid) {
					
					//create token
					
					UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken
							(username, usr.getPassword(),usr.getAuthorities());
					
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					//storing 
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
			
		}
		filterChain.doFilter(request, response);
	}
	
}
