package com.camera.API.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
	private int id;
	private String name;
	private String password;
	private int apartmentId;
	private int daireNo;
}
