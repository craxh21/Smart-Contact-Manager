package com.example.service;

import com.example.dao.UserRepository;
import com.example.entities.Contact;
import com.example.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@Service
public class ContactService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void addContact(String username, Contact contact, MultipartFile file) {
        User user = userRepository.getUserByUserName(username);

        // Handle file upload
        if (!file.isEmpty()) {
            try {
                // Step 1: Generate unique filename
                String originalFilename = file.getOriginalFilename();
                String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;

                // Step 2: Get upload directory
                File uploadDir = new ClassPathResource("static/images").getFile();
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs(); // create directory if not exists
                }

                // Step 3: Create full path
                Path path = Paths.get(uploadDir.getAbsolutePath(), uniqueFilename);

                // Step 4: Copy file
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                // Step 5: Set image name to contact
                contact.setImage(uniqueFilename);

                System.out.println("✅ Image uploaded to: " + path.toString());

            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image", e);
            }
        } else {
            System.out.println("⚠️ Uploaded file is empty.");
            contact.setImage("default.png"); // Optional fallback image
        }

        // Set the relationship and save
        contact.setUser(user);
        user.getContacts().add(contact);
        userRepository.save(user);

        System.out.println("✅ Contact saved successfully.");
    }
}
