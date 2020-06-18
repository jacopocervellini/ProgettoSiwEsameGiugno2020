package com.example.demo.controller;

import javax.validation.Valid;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.User;
import com.example.demo.model.UserCredentials;
import com.example.demo.services.UserCredentialsService;
import com.example.demo.session.SessionData;
import com.example.demo.validator.CredentialsValidator;
import com.example.demo.validator.UserValidator;




@Controller
public class AuthenticationController {


	public AuthenticationController() {

	}

	@Autowired
	private UserCredentialsService credentialsService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private CredentialsValidator credentialsValidator;

	@Autowired
	private SessionData sessionData;
	

	@RequestMapping(value = "/users/register",method=RequestMethod.POST)
	public String userRegister(@Valid @ModelAttribute("userForm") User user,
			BindingResult userBindingResult,
			@Valid @ModelAttribute("credentialsForm") UserCredentials credentials,
					BindingResult credentialsBindingResult,Model model) {

		this.userValidator.validate(user,userBindingResult);
		this.credentialsValidator.validate(credentials,credentialsBindingResult);
	
	if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
		credentials.setUser(user);
		this.credentialsService.saveCredentials(credentials);
		return "registrationSuccesful";
	}
	return "registerUser";
}
	
	@RequestMapping(value ="/users/register",method=RequestMethod.GET)
	public String userRegisters(@Valid @ModelAttribute("userForm") User user,
			BindingResult userBindingResult,
			@Valid @ModelAttribute("credentialsForm") UserCredentials credentials,
					BindingResult credentialsBindingResult,Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("credentialsForm", new UserCredentials());
		return "registerUser";
	}
	
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(Model model) {
		this.sessionData.clear();
		return "index";
	}

}