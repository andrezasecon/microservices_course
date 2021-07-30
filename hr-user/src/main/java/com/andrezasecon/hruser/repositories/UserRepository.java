package com.andrezasecon.hruser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andrezasecon.hruser.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);

}
