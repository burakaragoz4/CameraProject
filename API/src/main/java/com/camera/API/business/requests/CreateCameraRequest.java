package com.camera.API.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCameraRequest {
	private String cameraName;
	private int apartmentId;
}
