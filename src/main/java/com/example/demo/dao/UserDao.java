package com.example.demo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;

@Repository
public interface UserDao extends CrudRepository<User, String>{
	
	@Query(value = "select userId from user where userName = ?1", nativeQuery = true)
    int findUserIdUsingUserName(String userName);
	
	@Query(value = "select * from user where user_name = ?1", nativeQuery = true)
    User findUserDetailUsingUserName(String userName);

}
