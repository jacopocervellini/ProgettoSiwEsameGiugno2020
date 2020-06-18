package com.example.demo.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;
import com.example.demo.model.UserCredentials;
import com.example.demo.repository.UserCredentialsRepository;

@Component
@Scope(value="session",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class SessionData {

	private User user;
	
	private UserCredentials credentials;
	
	
	@Autowired
	private UserCredentialsRepository repository;
	
	
	public User getLoggedUser() {
		if(this.user==null)
			this.update();
		return this.user;
	}
	
	
	private void update() {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails crede= (UserDetails) obj;
		this.credentials= this.repository.findByUsername(crede.getUsername()).get();
		this.credentials.setPassword("[PROTECTED]");
		this.user= this.credentials.getUser();
		
		
	}


	public UserCredentials getLoggedCredentials() {
		if(this.credentials==null)
			this.update();
		return this.credentials;
	}
	
	public void clear() {
		this.credentials = null;
		this.user = null;
	}
}
