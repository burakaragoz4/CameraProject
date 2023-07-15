package com.camera.API.business.responses;

import java.util.List;

import com.camera.API.entities.concretes.Camera;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdUserResponse {
	private int id;
	private String name;
	private String blokeName;
	private List<Camera> cameras;
	private int daireNo;
}
