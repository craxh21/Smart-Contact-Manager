package com.example.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.dao.UserRepository;
import com.example.entities.Contact;
import com.example.entities.User;
import com.example.helper.Message;
import com.example.service.ContactService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@ModelAttribute
	public void addCommonDaat(Model model , Principal principal){

		String username = principal.getName();  //username is the email
		System.out.println("username:" + username);

		User user = userRepository.getUserByUserName(username);
		// System.out.println("USER data: "+ user.toString());

		model.addAttribute("user", user); //by this we can use the user object in html pages
	}

	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("title","User dashboard");
		return "normal/user_dashboard";
	}

	@GetMapping("/add_contact")
	public String openAddContact(Model model){
		model.addAttribute("title","Add Contact");
		model.addAttribute("contact", new Contact());

		return "normal/add_contact";
	}

	// processing add contact
	@Autowired
	private ContactService contactService;

	@PostMapping("/process_contact")
	public String processAddContact(
			@ModelAttribute Contact contact,
			@RequestParam("profileImage") MultipartFile file,
			Principal principal,
			RedirectAttributes redirectAttributes
	) {
		try {
			String name = principal.getName();
			contactService.addContact(name, contact, file);

			redirectAttributes.addFlashAttribute("message", new Message("Contact added!", "success"));
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", new Message("Something went wrong !!  try again ...", "danger"));
		}

		return "redirect:/user/add_contact";  // ⬅ Redirect to trigger flash
	}

	
}
