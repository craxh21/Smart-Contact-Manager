package com.example.service;

import com.example.dao.UserRepository;
import com.example.entities.Contact;
import com.example.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void addContact(String username, Contact contact) {
        User user = userRepository.getUserByUserName(username);
        contact.setUser(user);
        user.getContacts().add(contact);
        userRepository.save(user);
    }
}
