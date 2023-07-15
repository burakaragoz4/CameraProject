package com.camera.API.business.abstaracts;


import java.util.List;

import com.camera.API.business.requests.CreateLogRequest;
import com.camera.API.business.responses.GetAllLogsResponse;
import com.camera.API.business.responses.GetByIdLogResponse;

public interface LogService {

	boolean add(CreateLogRequest createLogRequest,String token);

	List<GetAllLogsResponse> getAll(String token);

	GetByIdLogResponse getById(int id,String token);

}
