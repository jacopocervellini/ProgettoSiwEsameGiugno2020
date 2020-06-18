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

import com.example.demo.model.Comment;
import com.example.demo.model.Project;
import com.example.demo.model.Tag;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskRepository;
import com.example.demo.services.CommentService;
import com.example.demo.services.ProjectService;
import com.example.demo.services.TagService;
import com.example.demo.services.TaskService;
import com.example.demo.session.SessionData;
import com.example.demo.validator.CommentValidator;
import com.example.demo.validator.TagValidator;
import com.example.demo.validator.TaskValidator;

@Controller
public class TaskController {

	@Autowired 
	private SessionData sessionData;

	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private TaskValidator validator;

	@Autowired
	private TagValidator tagValidator;
	
	@Autowired
	private CommentValidator commentValidator;

	public TaskController() {

	}


	@RequestMapping(value= {"progetto/{id}/addTask"},method=RequestMethod.GET)
	public String taskForm (Model model, @PathVariable ("id") Long id) {
		Project project = this.projectService.getProject(id);
		model.addAttribute("taskForm", new Task());
		model.addAttribute("project",project);
		return"taskForm";
	}

	@RequestMapping(value= {"progetto/{id}/addTask"},method=RequestMethod.POST)
	public String addTask (Model model, @PathVariable ("id") Long id,
			@Valid @ModelAttribute("taskForm") Task task, BindingResult taskBindingResult)
	{
		User userLoggato =this.sessionData.getLoggedUser();
		Project project = this.projectService.getProject(id);
		Task t=new Task();
		this.validator.validate(task,taskBindingResult);
		if(!taskBindingResult.hasErrors()) {
		if(project.getOwner().equals(userLoggato)) {
		t.setName(task.getName());
		t.setDescription(task.getDescription());
		t.setProject(project);
		this.taskService.saveTask(t);
		project.getOwnertask().add(t);
		this.projectService.saveProject(project);
		model.addAttribute("userLoggato",userLoggato);
		model.addAttribute("project",project);
		model.addAttribute("tasks",project.getOwnertask());
		return"redirect:/progetto/"+project.getId().toString();}
		else 
			return"redirect:/progetto/"+project.getId().toString();}
		else 
			model.addAttribute("project",project);
		return "taskForm";
	}

	@RequestMapping(value= {"task/{id}"},method=RequestMethod.GET)
	public String task (Model model, @PathVariable ("id") Long id) {
		Task t = this.taskService.getTask(id);
		model.addAttribute("task", t);
		model.addAttribute("tags",t.getTaskTags());
		return"task";
	}

	@RequestMapping(value= {"elimina/task/{id}"},method=RequestMethod.GET)
	public String deleteTask (Model model, @PathVariable ("id") Long id) {
		Task t= this.taskService.getTask(id);
		Project project=t.getProject();
		this.taskService.deleteTask(t);
		return"redirect:/progetto/"+ project.getId();
	}

	@RequestMapping(value= {"editTask/{id}"},method= RequestMethod.GET)
	public String editTaskForm(Model model,@PathVariable ("id") Long id)	{
		Task task=this.taskService.getTask(id);
		model.addAttribute("task", task);
		model.addAttribute("taskForm", new Task());
		return "editTask";
	}

	@RequestMapping(value= {"editTask/{id}"},method= RequestMethod.POST)
	public String editTask(Model model,@PathVariable ("id") Long id, 
			@Valid @ModelAttribute("taskForm") Task taskForm, BindingResult taskBindingResult) {
		Task task=this.taskService.getTask(id);
		this.validator.validate(taskForm,taskBindingResult);
		if(!taskBindingResult.hasErrors()) {
		task.setName(taskForm.getName());
		task.setDescription(taskForm.getDescription());
		Project project= task.getProject();
		this.taskRepository.save(task);
		model.addAttribute("tasks",project.getOwnertask());
		model.addAttribute("project",project);
		return "redirect:/progetto/"+ project.getId().toString();}
		else 
			model.addAttribute("task", task);
		return "editTask";
	}
	
	@RequestMapping(value= {"/task/{idTask}/addTag"},method=RequestMethod.GET)
	public String TagForm(Model model,@PathVariable("idTask") Long idTask) {
		Task task= this.taskService.getTask(idTask);
		model.addAttribute("tagForm",new Tag());
		model.addAttribute("task",task);
		return "tagFormTask";
	}
	
	@RequestMapping(value= {"/task/{idTask}/addTag"},method=RequestMethod.POST)
	public String TagForTask(Model model,@PathVariable("idTask") Long idTask,
			@Valid @ModelAttribute("tagForm") Tag tag, BindingResult tagBindingResult) {
		Task task= this.taskService.getTask(idTask);
		Tag t=new Tag();
		User userLoggato=this.sessionData.getLoggedUser();
		Project project= task.getProject();
		this.tagValidator.validate(tag,tagBindingResult);
		if(!tagBindingResult.hasErrors()) {
		if(project.getOwner().equals(userLoggato)) {
		t.setName(tag.getName());
		t.setDescription(tag.getDescription());
		t.setColor(tag.getColor());
		task.getTaskTags().add(t);
		t.getOwnerTask().add(task);
		this.taskService.saveTask(task);
		this.tagService.saveTag(t);
		model.addAttribute("task",task);
		model.addAttribute("tags",task.getTaskTags());
		return"redirect:/task/"+task.getId().toString();
	} else 
		return "redirect:/task/"+task.getId().toString();
	}
		else 
			model.addAttribute("task",task);
			return "tagFormTask";
		}
	
	
	@RequestMapping(value= {"/task/{idTask}/addComment"},method=RequestMethod.GET)
	public String CommentForm(Model model,@PathVariable("idTask") Long idTask) {
		Task task= this.taskService.getTask(idTask);
		model.addAttribute("commentForm",new Comment());
		model.addAttribute("task",task);
		return "commentForm";
	}
	
	@RequestMapping(value= {"/task/{idTask}/addComment"},method=RequestMethod.POST)
	public String CommentForTask(Model model,@PathVariable("idTask") Long idTask,
			@Valid @ModelAttribute("commentForm") Comment comment, BindingResult commentBindingResult) {
		Task task= this.taskService.getTask(idTask);
		Comment c = new Comment();
		User userLoggato=this.sessionData.getLoggedUser();
		Project project= task.getProject();
		this.commentValidator.validate(comment,commentBindingResult);
		if(!commentBindingResult.hasErrors()) {
		if(!project.getOwner().equals(userLoggato)) {
		c.setText(comment.getText());
		task.getCommentTask().add(c);
		this.commentService.saveComment(c);
		this.taskService.saveTask(task);
		model.addAttribute("task",task);
		model.addAttribute("comments",task.getCommentTask());
		return"redirect:/task/"+task.getId().toString();
	} else 
		return "redirect:/task/"+task.getId().toString();
	} else 
		model.addAttribute("task", task);
		return "commentForm";
	}
	
	
}
