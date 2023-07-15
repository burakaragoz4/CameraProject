package com.camera.API.webApi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.camera.API.business.abstaracts.ApartmentService;
import com.camera.API.business.requests.CreateApartmentRequest;
import com.camera.API.business.responses.GetAllApartmentsResponse;
import com.camera.API.business.responses.GetByIdApartmentResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {
	private ApartmentService apartmentService;

	@PostMapping("/createApartment")
	@ResponseStatus(code = HttpStatus.CREATED)
	public boolean add(@RequestBody CreateApartmentRequest createApartmentRequest,
			@RequestHeader("Authorization") String token) {
		return this.apartmentService.add(createApartmentRequest,token);

	}

	@GetMapping("/allApartment")
	public List<GetAllApartmentsResponse> getAll(@RequestHeader("Authorization") String token) {
		return apartmentService.getAll(token);
	}

	@GetMapping("/{id}")
	public GetByIdApartmentResponse getById(@PathVariable int id, @RequestHeader("Authorization") String token) {
		return apartmentService.getById(id,token);

	}

	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable int id, @RequestHeader("Authorization") String token) {
		return this.apartmentService.delete(id,token);

	}

}
