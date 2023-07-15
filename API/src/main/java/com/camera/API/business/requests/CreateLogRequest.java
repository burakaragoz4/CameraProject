package com.camera.API.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLogRequest {
	@NotNull
	@NotBlank
	private int userId;
	private int logEnterTime;
	private int logStopTime;
	private int totalTime;
	private int logEntry;
	private int logExit;
}
