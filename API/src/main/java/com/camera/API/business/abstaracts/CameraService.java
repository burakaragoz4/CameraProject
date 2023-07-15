package com.camera.API.business.abstaracts;

import java.util.List;

import com.camera.API.business.requests.CreateCameraRequest;
import com.camera.API.business.requests.UpdateCameraRequest;
import com.camera.API.business.responses.GetAllCamerasResponse;
import com.camera.API.business.responses.GetByIdCameraResponse;

public interface CameraService {
	List<GetAllCamerasResponse> getAll(String token);

	GetByIdCameraResponse getById(int id,String token);

	boolean add(CreateCameraRequest cameraRequest,String token);

	boolean update(UpdateCameraRequest cameraRequest,String token);

	boolean delete(int id,String token);
}
