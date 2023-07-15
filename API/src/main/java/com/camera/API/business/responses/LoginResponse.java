package com.camera.API.business.responses;


import java.util.List;

import com.camera.API.entities.concretes.Camera;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
	private String userName;
	private String password;
	private String blokName;
	private List<Camera> channelNames;
	private String token;
}
