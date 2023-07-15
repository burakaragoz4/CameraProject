package com.camera.API.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllLogsResponse {
	private int id;
	private int userId;
	private int logEnterTime;
	private int logStopTime;
	private int totalTime;
	private int logEntry;
	private int logExit;
}
