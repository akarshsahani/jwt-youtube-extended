package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Role;

@Repository
public interface RoleDao extends CrudRepository<Role, Long>{

}
