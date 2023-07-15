package com.camera.API.webApi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.camera.API.business.abstaracts.UserService;
import com.camera.API.business.requests.CreateUserRequest;
import com.camera.API.business.requests.LoginUserRequest;
import com.camera.API.business.requests.UpdateUserRequest;
import com.camera.API.business.responses.GetAllUsersResponse;
import com.camera.API.business.responses.GetByIdUserResponse;
import com.camera.API.business.responses.LoginResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {
	private UserService userService;


	@PostMapping("/userLogin")
	public LoginResponse login(@RequestBody LoginUserRequest loginUserRequest) {
		return this.userService.login(loginUserRequest.getName(), loginUserRequest.getPassword());
	}

	@PostMapping("/createUser")
	@ResponseStatus(code = HttpStatus.CREATED)
	public boolean add(@RequestBody CreateUserRequest createUserRequest) {
		return this.userService.add(createUserRequest);
		
	}

	@GetMapping("/allUsers")
	public List<GetAllUsersResponse> getAll(@RequestHeader("Authorization") String token) {
		return userService.getAll(token);
	}

	@GetMapping("/{id}")
	public GetByIdUserResponse getById(@PathVariable int id, @RequestHeader("Authorization") String token) {
		return userService.getById(id, token);

	}

	@PutMapping("/updateUser")
	public boolean update(@RequestBody UpdateUserRequest updateUserRequest, @RequestHeader("Authorization") String token) {
		return this.userService.update(updateUserRequest, token);

	}

	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable int id, @RequestHeader("Authorization") String token) {
		return this.userService.delete(id, token);
	}
}
