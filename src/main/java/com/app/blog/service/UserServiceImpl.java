package com.app.blog.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.app.blog.models.User;
import com.app.blog.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService,UserDetailsService{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository usrrepo;
	
 

	@Override
	public User saveUser(User user) {

		//encode psw
		user.setPassword(
				passwordEncoder.encode(user.getPassword())
				);
		return usrrepo.save(user);
	}

	@Override
	public List<User> getAllUsers() {

		return usrrepo.findAll();
	}

	@Override
	public Optional<User> findByUsername(String name) {
		return usrrepo.findByUsername(name);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> opt = usrrepo.findByUsername(username);
		
		if(opt.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}
		
	User user=opt.get();
		return new org.springframework.security.core.userdetails.User(
				username, user.getPassword(), 
				user.getPosts().stream()
				.map(post->new SimpleGrantedAuthority(post))
			.toList());
		
		
	
	}

	@Override
	public Set<String> getPosts(String username) {
		Optional<User> opt = usrrepo.findByUsername(username);
		User user=opt.get();
		return user.getPosts();
		}
	
	

}
