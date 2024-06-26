package com.example.smartcontactmanager.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.smartcontactmanager.Entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    @Query("select u from User u where u.email = :email")
    public User getUserByUserEmail(@Param("email") String email);
}
