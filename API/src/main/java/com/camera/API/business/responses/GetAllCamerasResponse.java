package com.camera.API.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCamerasResponse {
	private int id;
	private String cameraName;
	private String blokName;
}
