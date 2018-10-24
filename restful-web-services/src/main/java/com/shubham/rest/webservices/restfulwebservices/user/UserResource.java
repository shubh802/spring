package com.shubham.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {

	@Autowired
	private UserDaoService service;

	//GET /users
	//retrieveAllUsers
	@GetMapping(path="/users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}


	@GetMapping(path="/users/{id}") 
	public Resource<User> retrieveUser(@PathVariable int id) {
		User user = service.findOne(id);
		if (user == null) {
			throw new UserNotFoundException("id-"+ id);
		}

		// We need to create a link to return "all-users", SERVER_PATH + "/users"
		//What we will do is add the link to the method that is retrieveAllUsers
		Resource<User> resource = new Resource<User>(user);
		
		// We will link to this class and method retrieveAllUsers
		ControllerLinkBuilder linkTo =
				linkTo(methodOn(this.getClass()).retrieveAllUsers());
		// What we want to call the link the resource i.e. all-users
		resource.add(linkTo.withRel("all-users"));
		
		//HATEOS as we want to retrieve all the user details
		return resource;
	}

	//input - details of the user
	//output - Created & return the created URI when we have to return the user that is created

	//HATEOS

	@PostMapping("/users")
	public ResponseEntity createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		// Created
		URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
				buildAndExpand(savedUser.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	//Deleting a user
	@DeleteMapping(path="/users/{id}") 
	public void deleteUser(@PathVariable int id) {
		User user = service.deleteById(id);

		if (user == null) {
			throw new UserNotFoundException("id-"+ id);
		}		
	}
	
	
	
}
