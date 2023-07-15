package com.camera.API.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.camera.API.business.abstaracts.ApartmentService;
import com.camera.API.business.requests.CreateApartmentRequest;
import com.camera.API.business.responses.GetAllApartmentsResponse;
import com.camera.API.business.responses.GetByIdApartmentResponse;
import com.camera.API.core.utilities.jwt.JwtUtil;
import com.camera.API.core.utilities.mappers.ModelMapperService;
import com.camera.API.dataAccess.abstracts.ApartmentRepository;
import com.camera.API.entities.concretes.Apartment;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ApartmentManager implements ApartmentService {
	private final ApartmentRepository apartmentRepository;
	private final ModelMapperService modelMapperService;
	private final JwtUtil jwtUtil;

	public boolean isTokenValid(String token) {
		try {
			String username = jwtUtil.getUsernameFromToken(token);
			return username != null && jwtUtil.isTokenValidForUser(token, username);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean add(CreateApartmentRequest createApartmentRequest,String token) {
		if (!isTokenValid(token)) {
			throw new RuntimeException("Tokenın Süresi Geçmiş");
		}
		try {
			Apartment apartment = modelMapperService.forRequest().map(createApartmentRequest, Apartment.class);
		    apartmentRepository.save(apartment);
		    return true;
		}catch (Exception e) {
			 return false;
		}    
	}

	@Override
	public List<GetAllApartmentsResponse> getAll(String token) {
		if (!isTokenValid(token)) {
			throw new RuntimeException("Tokenın Süresi Geçmiş");
		}
		List<Apartment> apartments = apartmentRepository.findAll();
		if(apartments == null) {
			throw new RuntimeException("Apartman Bulunamadı");
		}
		List<GetAllApartmentsResponse> apartmentResponses = apartments.stream()
				.map(apartment -> modelMapperService.forResponse().map(apartment, GetAllApartmentsResponse.class))
				.collect(Collectors.toList());
		return apartmentResponses;
	}

	@Override
	public GetByIdApartmentResponse getById(int id, String token) {
		if (!isTokenValid(token)) {
			throw new RuntimeException("Tokenın Süresi Geçmiş");
		}
		Apartment apartment = apartmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Apartman bulunamadı."));
		GetByIdApartmentResponse apartmentResponse = modelMapperService.forResponse().map(apartment, GetByIdApartmentResponse.class);
		return apartmentResponse;
	}

	@Override
	public boolean delete(int id, String token) {
	    if (!isTokenValid(token)) {
	        throw new RuntimeException("Tokenın Süresi Geçmiş");
	    }

	    Apartment apartment = apartmentRepository.findById(id).orElse(null);
	    if (apartment == null) {
	        throw new RuntimeException("Silinecek apartman bulunamadı.");
	    }
	    try {
	        apartmentRepository.deleteById(id);
	        return true;
	    } catch (Exception e) {
	        throw new RuntimeException("Apartman silme işlemi sırasında bir hata oluştu: " + e.getMessage());
	    }
	}


}
