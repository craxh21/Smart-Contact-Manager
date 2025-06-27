package com.example.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dao.UserRepository;
import com.example.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		String username = principal.getName();  //username is the email
		System.out.println("username:" + username);

		User user = userRepository.getUserByUserName(username);
		// System.out.println("USER data: "+ user.toString());

		model.addAttribute("user", user); //by this we can use the user object in html pages

		return "normal/user_dashboard";
	}
}
