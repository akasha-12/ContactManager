package com.smart.controllers;

import javax.servlet.http.HttpSession;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;



@Controller
public class SmartContactController {
	
	/*
	 * @Autowired private UserRepository userRepository;
	 * 
	 * @GetMapping("/file") public String handler() {
	 * 
	 * 
	 * return "file1";
	 * 
	 * }
	 * 
	 * 
	 * 
	 * @GetMapping("/test")
	 * 
	 * @ResponseBody public String handler1() {
	 * 
	 * User user=new User(); user.setName("ram saini");
	 * user.setEmail("ramsaini364@gmail.com"); user.setPassword("ram22345@#$");
	 * userRepository.save(user);
	 * 
	 * return "working";
	 * 
	 * }
	 */
    @Autowired
	private UserRepository userRepository;
    
    
	
	  @Autowired
	  private BCryptPasswordEncoder passwordEncoder;
	 
    
    
	@RequestMapping("/")
	public String handler1(Model model) {
		model.addAttribute("title", "this is the home page title");
		
		return "index";
		
	}
	
	/*
	 * @RequestMapping("/about") public String about(Model model) {
	 * 
	 * model.addAttribute("title", "this is the about page title"); return "about";
	 * }
	 */
	
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("user",new User());
		model.addAttribute("title", "Register here");
		return "signup";
	}
	
	
	// this handler for registering the user
	
	@RequestMapping(value="/do_register",method = RequestMethod.POST)
	public String register(@Valid @ModelAttribute("user") User user,BindingResult bindingResult , Model model,  HttpSession session){
		
		

		try {
			
			
			
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			System.out.println(user);
			
			if(bindingResult.hasErrors())
			{
				
				System.out.println("ERROR"+bindingResult.toString());
				model.addAttribute("user",user );
				return "signup";
				
			}
			
			
			
		    User result=	this.userRepository.save(user);
		    
			model.addAttribute("user", result);
			model.addAttribute("message", new Message("Successfully registered !!", "alert-success"));
			
		     return "success1";
			
		} catch (Exception e) {
			
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("something went wrong!!"+e.getMessage(), "alert-danger"));
		    return "signup";

		}
		
		
	}
	
	
	// handler for login
	
	
	
	@GetMapping("/signin")
	public String login(Model model) {
		
		
		
		 model.addAttribute("title", "Login page");
		
		 return "login";
	}
	
	
	@GetMapping("/help")
	public String help(Model model) {
		
		
		model.addAttribute("title", "help");
		return "help";
	}
	
	
	
	
	
}
