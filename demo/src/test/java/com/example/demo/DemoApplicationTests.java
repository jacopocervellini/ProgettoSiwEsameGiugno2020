package com.example.demo;



import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.ProjectService;
import com.example.demo.services.UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
class DemoApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectService projectService;



	@Before
	public void deleteAll() {
		System.out.println("Deleting all data...");
		this.projectRepository.deleteAll();
		this.taskRepository.deleteAll();
		this.userRepository.deleteAll();
		System.out.println("Done.");
	}


	@Test
	void testUpdateUser() {
		User user1= new User("Mario","Rossi");
		user1=userService.saveUser(user1);
		assertEquals(user1.getId().longValue(), 1L);
		User user2= new User("Luca","Bianca");
		user2=userService.saveUser(user2);
		assertEquals(user2.getId().longValue(), 2L);
		User user1Update= new User("Maria","Rossi");
		user1Update.setId(user1.getId());
		user1Update=userService.saveUser(user1Update);
		assertEquals(user1Update.getId().longValue(), 1L);
		assertEquals(user1Update.getFirstName(),"Maria");

		Project project1= new Project("testProject1","è il testproject1");
		project1.setOwner(user1Update);
		project1=this.projectRepository.save(project1);
		assertEquals(project1.getOwner(),user1Update);
		assertEquals(project1.getName(),"testProject1");

		Project project2= new Project("testProject2","è il testproject2");
		project2.setOwner(user1Update);
		project2=this.projectRepository.save(project2);
		assertEquals(project2.getOwner(),user1Update);
		assertEquals(project2.getName(),"testProject2");

		project1=this.projectService.shareProjectWithUser(project1,user2);
		List<Project> projects= projectRepository.findByOwner(user1Update);
		assertEquals(projects.size(), 2);
		assertEquals(projects.get(0), project1);
		assertEquals(projects.get(1), project2);
		assertEquals(1,user2.getVisibleProjects().size());

		projects= this.projectRepository.findByMembers(user2);
		assertEquals(1,projects.size());
		assertEquals(project1,projects.get(0));





	}

}
