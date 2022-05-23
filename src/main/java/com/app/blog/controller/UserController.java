package com.app.blog.controller;


import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.blog.models.User;
import com.app.blog.models.UserRequest;
import com.app.blog.models.userResponse;
import com.app.blog.service.UserService;
import com.app.blog.util.JWTUtil;



@RestController

public class UserController {
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private JWTUtil util;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	

	@PostMapping("/register")
	public ResponseEntity<String> registerUser( @RequestBody User user) {
	
		userservice.saveUser(user);
		return ResponseEntity.ok("User registered successfully");
	}
	
	
	@GetMapping("/users")
	public List<User> allUsers(){
		return userservice.getAllUsers();
			}

	
	@PostMapping("/login")
	public ResponseEntity<userResponse> loginUser(@RequestBody UserRequest request){
		
		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(), request.getPassword()));
		
		String token= util.GenerateToken(request.getUsername());
		return ResponseEntity.ok(new userResponse(token,"Successuffly generated!!"));
	}
	
	@PostMapping("/posts/{username}")
//	public ResponseEntity<String> AccessData(Principal p){
//		return ResponseEntity.ok("Welcome!!!"+p.getName());
//	}
	public Set<String> posts(@PathVariable String username){
		return userservice.getPosts(username);
			}
	
}
