package com.techweb.onlinetest.dao;

import org.springframework.data.repository.CrudRepository;

import com.onlinetest.model.User;


public interface UserDao extends CrudRepository<User, Long> {

	public User findByEmail(String email);

	public User findByName(String name);

}
