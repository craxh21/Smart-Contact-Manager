package com.example.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.entities.User;

public class CustomUserDetails implements UserDetails {


	private User user;

	public CustomUserDetails(User user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRole());
		return List.of(simpleGrantedAuthority);
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {

		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		
//		return UserDetails.super.isAccountNonExpired();
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		
//		return UserDetails.super.isAccountNonLocked();
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		
//		return UserDetails.super.isCredentialsNonExpired();
		return true;
	}
	
	@Override
	public boolean isEnabled() {
	
//		return UserDetails.super.isEnabled();
		return true;
	}
}
