package com.example.demo.controller;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.Project;
import com.example.demo.model.Tag;
import com.example.demo.model.User;
import com.example.demo.services.ProjectService;
import com.example.demo.services.TagService;
import com.example.demo.session.SessionData;
import com.example.demo.validator.TagValidator;


@Controller
public class TagController {



	@Autowired
	private SessionData sessionData;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private TagService tagService;

	@Autowired 
	private TagValidator validator;


	public TagController() {

	}


	@RequestMapping(value= {"progetto/{id}/addTag"},method=RequestMethod.GET)
	public String addTagForm(Model model,@PathVariable ("id") Long id) {
		Project project = this.projectService.getProject(id);
		model.addAttribute("tagForm",new Tag());
		model.addAttribute("project",project);
		return "tagForm";

	}

	@RequestMapping(value= {"progetto/{id}/addTag"},method=RequestMethod.POST)
	public String addTag (Model model, @PathVariable ("id") Long id,
			@Valid @ModelAttribute("tagForm") Tag tag, BindingResult bindingResult)
	{
		User userLoggato =this.sessionData.getLoggedUser();
		Project project = this.projectService.getProject(id);
		Tag t=new Tag();
		this.validator.validate(tag,bindingResult);
		if(!bindingResult.hasErrors()) {
			t.setName(tag.getName());
			t.setDescription(tag.getDescription());
			t.setColor(tag.getColor());
			this.tagService.saveTag(t);
			project.getProjectTags().add(t);
			this.projectService.saveProject(project);
			model.addAttribute("project",project);
			model.addAttribute("tasks",project.getOwnertask());
			model.addAttribute("userLoggato",userLoggato);
			model.addAttribute("tags",project.getProjectTags());
			return"redirect:/progetto/"+project.getId().toString();
		}else 
			model.addAttribute("project",project);
		return "tagForm";
	}


	@RequestMapping(value= {"tag/{id}"},method=RequestMethod.GET)
	public String task (Model model, @PathVariable ("id") Long id) {
		Tag t = this.tagService.getTag(id);
		model.addAttribute("tag", t);
		return"tag";
	}

	@RequestMapping(value= {"tag/delete/{id}"},method=RequestMethod.GET)
	public String deleteTag (Model model, @PathVariable ("id") Long id) {
		return"";
	}


}
