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


import com.example.demo.model.Project;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.model.UserCredentials;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.services.ProjectService;
import com.example.demo.services.UserCredentialsService;
import com.example.demo.services.UserService;
import com.example.demo.session.SessionData;
import com.example.demo.validator.ProjectValidator;

@Controller
public class ProjectController {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private SessionData sessionData; 

	@Autowired
	private ProjectValidator validator;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserCredentialsService credentialsService;

	public ProjectController() {

	}

	@RequestMapping(value= "/projects",method= RequestMethod.GET)
	public String projects (Model model) {
		User loggedUser= this.sessionData.getLoggedUser();
		List<Project> projects = this.projectRepository.findByOwner(loggedUser);
		model.addAttribute("loggedUser",loggedUser);
		model.addAttribute("projects",projects);
		return "projects";
	}

	@RequestMapping(value= "/visibleProjects",method= RequestMethod.GET)
	public String visibleProjects (Model model) {
		User loggedUser= this.sessionData.getLoggedUser();
		List<Project> projects = this.projectRepository.findByMembers(loggedUser);
		model.addAttribute("loggedUser",loggedUser);
		model.addAttribute("projects",projects);
		return "visibleProjects";
	}

	@RequestMapping(value="/progetto/{id}", method= RequestMethod.GET)
	public String viewProject(Model model, @PathVariable("id") Long id) {
		User userLoggato =this.sessionData.getLoggedUser();
		Project project=this.projectService.getProject(id);
		if (project==null) {
			return "redirect:/projects";
		}
		List<Task> tasks= project.getOwnertask();
		List<User> users = this.userService.getMembers(project);
		if(!users.contains(userLoggato) && !project.getOwner().equals(userLoggato)) {
			return "redirect:/projects" ;
		}
		
		model.addAttribute("userLoggato",userLoggato);
		model.addAttribute("project",project);
		model.addAttribute("tasks", tasks);
		model.addAttribute("users",users);
		return "progetto";
	}


	@RequestMapping (value="/projects/add", method= RequestMethod.GET)
	public String ProjectForm(Model model) {
		User loggedUser=this.sessionData.getLoggedUser();
		model.addAttribute("loggeUser",loggedUser);
		model.addAttribute("projectForm",new Project());
		return "newProject";
	}

	@RequestMapping (value="/projects/add", method= RequestMethod.POST)
	public String newProject(@Valid @ModelAttribute("projectForm") Project project,
			BindingResult bindingResult,Model model) {
		User loggedUser= this.sessionData.getLoggedUser();
		this.validator.validate(project,bindingResult);
		if(!bindingResult.hasErrors()) {
			project.setOwner(loggedUser);
			this.projectRepository.save(project);
			model.addAttribute("loggedUser",loggedUser);
			return "redirect:/progetto/"+project.getId().toString();
		}else 	
			return "newProject";
	}
	
	@RequestMapping (value="/elimina/progetto/{id}", method= RequestMethod.GET)
	public String deleteProject(@PathVariable("id") Long id , Model model) {
		User userLoggato= this.sessionData.getLoggedUser();
		this.projectRepository.deleteById(id);
		List<Project> projects = this.projectRepository.findByOwner(userLoggato);
		model.addAttribute(userLoggato);
		model.addAttribute("projects",projects);
		return "redirect:/projects";
	}

	@RequestMapping (value="/editProgetto/{id}", method= RequestMethod.GET)
	public String editProjectForm(@PathVariable Long id , Model model) {
		User loggedUser=this.sessionData.getLoggedUser();
		Project project=this.projectService.getProject(id);
		model.addAttribute("project", project);
		model.addAttribute("loggeUser",loggedUser);
		model.addAttribute("projectForm",new Project());
		return "editProject";
	}
	

	@RequestMapping(value = { "/editProgetto/{id}"}, method = RequestMethod.POST)
    public String editProject(@Valid @ModelAttribute("projectForm") Project projectForm,
                              BindingResult projectBindingResult,
                              Model model,
                              @PathVariable("id") Long id) {
        // validate project fields
        this.validator.validate(projectForm, projectBindingResult);
       
        Project projecto = projectService.getProject(id);
        UserCredentials credentials= sessionData.getLoggedCredentials();
        projecto.setOwner(credentials.getUser());
        if(!projectBindingResult.hasErrors()) {
        	projecto.setName(projectForm.getName());
        	projecto.setDescription(projectForm.getDescription());
        	projecto.setOwner(credentials.getUser());
        	this.projectRepository.save(projecto);
        	model.addAttribute("project",projecto);
        	model.addAttribute("userLoggato",credentials.getUser());
        	model.addAttribute("tasks",projecto.getOwnertask());
        	return "redirect:/progetto/"+projecto.getId().toString();
        }else
        	model.addAttribute("project", projecto);
        	return "editProject";
	}
	
	@RequestMapping(value= {"/progetto/share/{id}"},method=RequestMethod.GET)
	public String shareProjectForm(Model model, @PathVariable("id") Long id){
		Project project=this.projectService.getProject(id);
		model.addAttribute("project", project);
		model.addAttribute("credentials",new UserCredentials());
		return "shareProject";
	}

	@RequestMapping(value= {"/progetto/share/{id}"},method=RequestMethod.POST)
	public String shareProjectForm(Model model, @PathVariable("id") Long id,
			@Valid@ModelAttribute("credentials") UserCredentials credentials){
		    Project project=this.projectService.getProject(id);
		    String username=credentials.getUsername();
			UserCredentials userCredentials=this.credentialsService.getCredentials(username);
			User user= userCredentials.getUser();
			if(user!=null) {
			project.getMembers().add(user);
			user.getVisibleProjects().add(project);
			this.projectRepository.save(project);
			model.addAttribute("project",project);
			return"redirect:/home";
	}	else 
		return "redirect:/ projects";
	}
}
