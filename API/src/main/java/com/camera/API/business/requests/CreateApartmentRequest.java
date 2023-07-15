package com.camera.API.business.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateApartmentRequest {
	@NotNull
	@NotBlank
	@Size(min = 1, max = 10)
	private String blokName;
}
