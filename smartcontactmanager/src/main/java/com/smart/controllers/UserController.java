package com.smart.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;

@Controller
@RequestMapping(value = "/user", method = { RequestMethod.GET, RequestMethod.POST })
public class UserController {
	
	
@Autowired
private UserRepository userRepository;


@Autowired
private ContactRepository contactRepository;

//method for adding the commondata to response
@ModelAttribute
public void addCommonData(Model model , Principal principal) {
	
	

	 String email=principal.getName();
	 System.out.println(email);
	
	
     User user=	userRepository.getUserByUserName(email);
	
	 model.addAttribute("user",user );
	
	
	
	
	
	
	
	
	
}
	
	@RequestMapping("/about")
	public String dashboard(Model model , Principal principal) {
		
		String email=principal.getName();
		System.out.println(email);
		
		
       User user=	userRepository.getUserByUserName(email);
		
		model.addAttribute("user",user );
		
		
		
		
		
		return "normal/about";
	}
	
	
	
	
//	open add form handler
	
	@GetMapping("/add-contact")
	public String openAddContactForm(Model model) {
		
		
		
		
	model.addAttribute("title", "add contact");
		
	model.addAttribute("contact",new Contact());
	
	
	return "normal/add_contact_form";	
		
	}
	
	
	// processingn the user addcontact data 
	
@RequestMapping(method = RequestMethod.POST,value="process-contact")
	public String processuserform(@ModelAttribute Contact contact, 
			                      @RequestParam("profileImage") MultipartFile file, 
			                      Principal principal,HttpSession session) {
		
		
	try {
		
		String name=	principal.getName();
		
	     User user=this.userRepository.getUserByUserName(name);
	     contact.setUser(user);
	     
	     // processing the file
	     
	     if(file.isEmpty()) {
	    	 
	    	 System.out.println("file is empty");
	    	 
	    	 // seting default image if user select none 
	    	 contact.setImage("contact.png");
	     }
	     
	     else {
	    	 
	    	 contact.setImage(file.getOriginalFilename());
	    	 
	    	 // finding the path of folder in which we wants to store the file
	    	 
	    	 
	       File saveFile=     new ClassPathResource("static/image").getFile();
	
           Path path=	Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
	
	
	       Files.copy(file.getInputStream(),path , StandardCopyOption.REPLACE_EXISTING);
	    	 
	    	System.out.println("image is uploaded sucessfuly"); 
	     }
	     
	     
	     user.getContacts().add(contact);
	     this.userRepository.save(user);

		 System.out.println("DATA="+contact);
	     System.out.println("added to database successfully");
	     
	     
	// message success	
	     
	   session.setAttribute("message", new Message("your contact added succesfully....", "success")) ;
		
	}
	
	catch (Exception e) {
	
		System.out.println("ERROR "+e);
	// message error
		   session.setAttribute("message", new Message("something went wrong..", "danger")) ;

		
	}
		return "normal/about";
	}
	
	
// shwoing the contacts from the contact list
// per page = 5[n]
// current page =0[page]

@GetMapping("/show-contacts/{page}")
public String showcontacts(@PathVariable("page") Integer page, Model model, Principal principal) {
	
	// finding the user emailid by the help of the principal
    String userName=	principal.getName();
    
    
    // finding the user all details 
    User user=userRepository.getUserByUserName(userName);
    
    
    //page request
    
 Pageable pageable=      PageRequest.of(page, 3);         
  
    
    
     // finding the contacts of the user by the help of contactrepository
     Page<Contact> contacts=    this.contactRepository.findContactByUser(user.getId(),pageable);
     
     
	 // sending the all contcts of the login user on view page
     model.addAttribute("contacts", contacts);
     
	model.addAttribute("currentpage", page);
	model.addAttribute("title", "show_contacts");
	model.addAttribute("totalpages", contacts.getTotalPages());
	return "normal/show_contacts";
	
	
}


// deleting the contact using the user id
@GetMapping("/delete/{cid}")
public String handler(@PathVariable("cid") Integer CID , Model model,HttpSession session) {
	
	
	Contact contact = this.contactRepository.findById(CID).get();
	contact.setUser(null);
	
	this.contactRepository.delete(contact);
	
	session.setAttribute("message", new Message("contact delete sucessfully", "success"));
    return "redirect:/user/show-contacts/0";	
	
}

// updating the specific user 

@PostMapping("/update-contact/{cid}")
public String updateForm(@PathVariable("cid") Integer cid,  Model m) {
	
	m.addAttribute("title", "update the form");
	Contact contact = this.contactRepository.findById(cid).get();
	
	
	
	
	m.addAttribute("contact", contact);
	
	return "normal/update_form";
}


// processing the update user

@PostMapping("/process-update")
public String processHandler(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,Principal principal,HttpSession session) {
	
	try {
		
	     User user = this.userRepository.getUserByUserName(principal.getName());
		 contact.setUser(user);
		 
		    if(file.isEmpty()) {
		    	 
		    	 System.out.println("file is empty");
		    	 
		    	 // seting default image if user select none 
		    	 contact.setImage("contact.png");
		     }
		     
		     else {
		    	 
		    	 contact.setImage(file.getOriginalFilename());
		    	 
		    	 // finding the path of folder in which we wants to store the file
		    	 
		    	 
		       File saveFile=     new ClassPathResource("static/image").getFile();
		
	           Path path=	Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
		
		
		       Files.copy(file.getInputStream(),path , StandardCopyOption.REPLACE_EXISTING);
		    	 
		    	System.out.println("image is uploaded sucessfuly"); 
		     }
		 
		 Contact save = this.contactRepository.save(contact);
		
		 session.setAttribute("message", new Message("contact UPDATE sucessfully", "success"));
		
	} catch (Exception e) {
		
	}
	
	return "redirect:/user/show-contacts/0";
}


}
