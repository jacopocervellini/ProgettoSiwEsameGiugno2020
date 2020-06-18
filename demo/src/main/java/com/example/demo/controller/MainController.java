package com.example.demo.controller;




import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.UserCredentials;

@Controller
public class MainController {
	
	
	public MainController() {
		
	}

	@RequestMapping(value= {"/","/index"}, method= RequestMethod.GET)
	public String index (Model model) {
		return "index";
	}
	
	@RequestMapping(value= {}, method=RequestMethod.GET)
	public String registration(Model model) {
		return "registrationSuccesful";
	}
	
	@RequestMapping(value= {"/login"}, method=RequestMethod.GET)
	public String loginForm(Model model) {
		model.addAttribute("credentialsForm",new UserCredentials());
		return "login";
	}
	
	

}
