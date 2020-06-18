package com.example.demo.controller;



import java.util.List;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.example.demo.model.User;
import com.example.demo.model.UserCredentials;
import com.example.demo.services.UserCredentialsService;
import com.example.demo.services.UserService;
import com.example.demo.session.SessionData;
import com.example.demo.validator.CredentialsValidator;
import com.example.demo.validator.UserValidator;



@Controller
public class UserController {

	@Autowired
	private SessionData sessionData;

	@Autowired
	private UserValidator validator;

	@Autowired
	private CredentialsValidator credentialsValidator;

	@Autowired
	private UserService service;

	@Autowired
	private UserCredentialsService credentialsService;




	public UserController() {

	}



	/*intercetta le richieste get a home e restituisce la vista "home" */
	@RequestMapping(value= {"/home"}, method=RequestMethod.GET)
	public String home (Model model) {
		User loggedUser= this.sessionData.getLoggedUser();
		model.addAttribute("user",loggedUser);
		return "home";
	}


	/*cattura le richieste get a admin e restituisce la vista "admin" */
	@RequestMapping(value= {"/admin"},method= RequestMethod.GET)
	public String admin(Model model) {
		User loggedUser= this.sessionData.getLoggedUser();
		model.addAttribute("user",loggedUser);
		return "admin";
	}

	@RequestMapping(value= {"/user/profile"},method= RequestMethod.GET)
	public String profile(Model model) {
		User userLoggato= this.sessionData.getLoggedUser();
		UserCredentials credentials = this.sessionData.getLoggedCredentials();
		model.addAttribute("credentials",credentials);
		model.addAttribute("userLoggato", userLoggato);
		return "profile";
	}

	@RequestMapping(value= {"user/profile/edit"},method= RequestMethod.GET)
	public String editprofileForm(Model model) {
		User userLoggato= this.sessionData.getLoggedUser();
		model.addAttribute("userLoggato",userLoggato);
		model.addAttribute("userForm",new User());
		model.addAttribute("credentialsForm", new UserCredentials());
		return "editProfile";
	}

	@RequestMapping(value= {"user/profile/edit"},method= RequestMethod.POST)
	public String editProfile(@Valid @ModelAttribute("userForm") User user,
			BindingResult userBindingResult,
			@Valid @ModelAttribute("credentialsForm") UserCredentials credentials,
			BindingResult credentialsBindingResult,Model model) {
		this.validator.validate(user, userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);

		if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			User userLoggato=this.sessionData.getLoggedUser();
			userLoggato.setFirstName(user.getFirstName());
			userLoggato.setLastName(user.getLastName());
			this.service.saveUser(userLoggato);
			UserCredentials userCredentials= this.sessionData.getLoggedCredentials();
			userCredentials.setPassword(credentials.getPassword());
			userCredentials.setUsername(credentials.getUsername());
			userCredentials.setUser(userLoggato);
			userCredentials.setRole(credentials.getRole());
			this.credentialsService.saveCredentials(userCredentials);
			model.addAttribute("userLoggato",userLoggato);
			model.addAttribute("credentials",userCredentials);
			return "redirect:/user/profile/";
		}
		return"editProfile";
	}

	@RequestMapping(value= {"/admin/users"}, method=RequestMethod.GET)
	public String getUsers(Model model) {
		User userLoggato= this.sessionData.getLoggedUser();
		List<UserCredentials> credentials=this.credentialsService.getAllCredentials();
		model.addAttribute("userLoggato", userLoggato);
		model.addAttribute("credentialsList",credentials);
		return "usersList";
	}

	@RequestMapping(value= {"{/admin/user/{username}/delete"}, method=RequestMethod.POST)
	public String deleteUser(Model model,@PathVariable ("username")String username) {
		this.credentialsService.deleteCredentials(username);
		return"redirect:/admin/users";
	}
}

