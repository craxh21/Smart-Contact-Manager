package com.example.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer>{

    @Query("Select c from Contact c where c.user.id =:userId")
    //pageable will have 2 object: 1. curr page , 2. contact per page say 5
    public Page<Contact> findContactsByUserId(@Param("userId")int userId, Pageable pageable);

}
