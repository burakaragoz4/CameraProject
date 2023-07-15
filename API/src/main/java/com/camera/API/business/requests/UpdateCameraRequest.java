package com.camera.API.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCameraRequest {
	private int id;
	private String cameraName;
	private int apartmentId;
}
