package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.Comment;

import com.example.demo.services.CommentService;

@Controller
public class CommentController {
	
	@Autowired
	private CommentService service;
	
	@RequestMapping(value= {"comment/{id}"},method=RequestMethod.GET)
	public String task (Model model, @PathVariable ("id") Long id) {
		Comment c= this.service.getComment(id);
		model.addAttribute("comment", c);
		return"comment";
	}

}
