package com.camera.API.webApi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.camera.API.business.abstaracts.LogService;
import com.camera.API.business.requests.CreateLogRequest;
import com.camera.API.business.responses.GetAllLogsResponse;
import com.camera.API.business.responses.GetByIdLogResponse;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/logs")
public class LogsController {
	private LogService logService;

	@PostMapping("/createLog")
	@ResponseStatus(code = HttpStatus.CREATED)
	public boolean add(@RequestBody CreateLogRequest createLogRequest, @RequestHeader("Authorization") String token) {
		return this.logService.add(createLogRequest, token);

	}

	@GetMapping("/allLogs")
	public List<GetAllLogsResponse> getAll(@RequestHeader("Authorization") String token) {
		return this.logService.getAll(token);

	}

	@GetMapping("/{id}")
	public GetByIdLogResponse getById(@PathVariable int id, @RequestHeader("Authorization") String token) {
		return this.logService.getById(id, token);
	}

}
