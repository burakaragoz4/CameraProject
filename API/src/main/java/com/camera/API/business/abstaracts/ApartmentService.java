package com.camera.API.business.abstaracts;


import java.util.List;

import com.camera.API.business.requests.CreateApartmentRequest;
import com.camera.API.business.responses.GetAllApartmentsResponse;
import com.camera.API.business.responses.GetByIdApartmentResponse;

public interface ApartmentService {

	boolean add(CreateApartmentRequest binaRequest,String token);

	List<GetAllApartmentsResponse> getAll(String token);

	GetByIdApartmentResponse getById(int id,String token);

	boolean delete(int id,String token);

}
