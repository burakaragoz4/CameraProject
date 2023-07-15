package com.camera.API.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdApartmentResponse {
	private int binaId;
	private String blokeName;
}
