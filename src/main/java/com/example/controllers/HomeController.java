package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dao.UserRepository;
import com.example.entities.User;
import com.example.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;	
	
    @GetMapping("/")
    public String home(Model m) {
    	m.addAttribute("title", "Smart Contact Manager");
    	return "home"; 
    }
	
    @GetMapping("/about")
    public String about(Model m) {
    	m.addAttribute("title", "About - Smart Contact Manager");
    	return "about"; 
    }
    
    @GetMapping("/signup")
    public String signup(Model m) {
    	m.addAttribute("title", "register - Smart Contact Manager");
    	m.addAttribute("user", new User());
    	return "signup"; 
    }
    
    @PostMapping("/do_register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
    		BindingResult result1, 
    		@RequestParam(value="agreement", defaultValue = "false") boolean agreement,
    		Model m, HttpSession session) {
    	
    	try {
    		
    		
    		if(result1.hasErrors()) {
    			System.out.println("Error: "+ result1);
    			m.addAttribute("user", user);
    			return "signup";
    		}
    		
    		if(!agreement) {
    			System.out.println("agree the terms !!");
    			throw new Exception("agree the terms !");
    		}
    		
    		
    		user.setRole("ROLE_USER");
    		user.setEnabled(true);
    		//user.setImageUrl("");
    		user.setPassword(passwordEncoder.encode(user.getPassword()));
    		
    		
    		User result = this.userRepository.save(user);	//use when you want to add user in the db
    		
//    		System.out.println("Agreememt " + agreement);
//    		System.out.println("User " + user);
    		
    		m.addAttribute("user", new User());
    		
//    		session.setAttribute("message", new Message("successfully registered. ", "alert-success"));  
    		//in Thymeleaf 3.1+, ${session} is no longer available by default.
    		
    		m.addAttribute("message", new Message("successfully registered.", "alert-success"));
    		return "signup";
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    		m.addAttribute("user", user); //makes the user object available inside your HTML like this: <form th:object="${user}" ... >

    		//    session.setAttribute("message", new Message("something went wrong "+ e.getMessage(), "alert-danger"));
    		m.addAttribute("message", new Message("something went wrong: " + e.getMessage(), "alert-danger"));

    		return "signup";
		}
    	
    }

	@GetMapping("/signin")
	public String customLogin(Model model){
		model.addAttribute("title: ", "Login Page");
		return "login";
	}

	@PostMapping("/signin")
	public String handleLogin(Model model){
		model.addAttribute("title: ", "Login Page");
		return "login";
	}
}
