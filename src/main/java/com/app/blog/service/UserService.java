package com.app.blog.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.app.blog.models.User;

public interface UserService {
	
	User saveUser(User user);

	List<User> getAllUsers();
	Optional<User> findByUsername(String name);
	Set<String> getPosts(String username);
}
