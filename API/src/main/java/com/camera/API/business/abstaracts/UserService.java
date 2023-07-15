package com.camera.API.business.abstaracts;


import java.util.List;

import com.camera.API.business.requests.CreateUserRequest;
import com.camera.API.business.requests.UpdateUserRequest;
import com.camera.API.business.responses.GetAllUsersResponse;
import com.camera.API.business.responses.GetByIdUserResponse;
import com.camera.API.business.responses.LoginResponse;

public interface UserService {
	List<GetAllUsersResponse> getAll(String token); // Response

	GetByIdUserResponse getById(int id,String token);

	boolean add(CreateUserRequest createUserRequest);

	boolean update(UpdateUserRequest updateUserRequest,String token);

	boolean delete(int id,String token);

	LoginResponse login(String name, String password);
}
