package com.example.smartcontactmanager.Dao;

import com.example.smartcontactmanager.Entities.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.security.PublicKey;
import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

//    @Query("from Contact as c where c.user.id =:userId")
//    public List<Contact> findContactByUser(@Param("userId") int userId);

    @Query("from Contact as c where c.user.id =:userId")
    public Page<Contact> findContactByUser(@Param("userId") int userId, Pageable pageable);
}
