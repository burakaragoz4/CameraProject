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

import com.camera.API.business.abstaracts.CameraService;
import com.camera.API.business.requests.CreateCameraRequest;
import com.camera.API.business.requests.UpdateCameraRequest;
import com.camera.API.business.responses.GetAllCamerasResponse;
import com.camera.API.business.responses.GetByIdCameraResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/cameras")
public class CamerasController {
	private CameraService cameraService;

	@GetMapping("/allCameras")
	public List<GetAllCamerasResponse> getAll(@RequestHeader("Authorization") String token) {
		return cameraService.getAll(token);

	}

	@GetMapping("/{id}")
	public GetByIdCameraResponse getById(@PathVariable int id, @RequestHeader("Authorization") String token) {
		return cameraService.getById(id, token);

	}

	@PostMapping("/createCamera")
	@ResponseStatus(code = HttpStatus.CREATED)
	public boolean add(@RequestBody CreateCameraRequest cameraRequest, @RequestHeader("Authorization") String token) {
		return this.cameraService.add(cameraRequest, token);

	}

	@PutMapping("/updateCamera")
	public boolean update(@RequestBody UpdateCameraRequest updateCameraRequest,
			@RequestHeader("Authorization") String token) {
		return this.cameraService.update(updateCameraRequest, token);

	}

	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable int id, @RequestHeader("Authorization") String token) {

		return this.cameraService.delete(id, token);

	}
}
