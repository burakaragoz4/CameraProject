package com.camera.API.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.camera.API.business.abstaracts.CameraService;
import com.camera.API.business.requests.CreateCameraRequest;
import com.camera.API.business.requests.UpdateCameraRequest;
import com.camera.API.business.responses.GetAllCamerasResponse;
import com.camera.API.business.responses.GetByIdCameraResponse;
import com.camera.API.core.utilities.jwt.JwtUtil;
import com.camera.API.dataAccess.abstracts.ApartmentRepository;
import com.camera.API.dataAccess.abstracts.CameraRepository;
import com.camera.API.entities.concretes.Apartment;
import com.camera.API.entities.concretes.Camera;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CameraManager implements CameraService {
	private CameraRepository cameraRepository;
	private final ApartmentRepository apartmentRepository;
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
	public List<GetAllCamerasResponse> getAll(String token) {
		if (!isTokenValid(token)) {
			throw new RuntimeException("Tokenın Süresi Geçmiş");
		}
		try {
			List<Camera> cameras = cameraRepository.findAll();
			if (cameras == null) {
				throw new RuntimeException("Kamera Bulanamadı");
			}
			List<GetAllCamerasResponse> camerasResponse = new ArrayList<>();
			for (Camera camera : cameras) {
				GetAllCamerasResponse response = new GetAllCamerasResponse();
				Apartment apartment = apartmentRepository.findById(camera.getApartmentId()).orElse(null);
				if (apartment == null) {
					response.setId(camera.getId());
					response.setCameraName(camera.getCameraName());
					response.setBlokName("SİLİNDİ");
					camerasResponse.add(response);
				} else {
					response.setId(camera.getId());
					response.setCameraName(camera.getCameraName());
					response.setBlokName(apartment.getBlokName());
					camerasResponse.add(response);
				}
			}

			return camerasResponse;
		} catch (Exception e) {
			throw new RuntimeException("Belirlenemeyen HATA" + e.getMessage());
		}

	}

	@Override
	public boolean add(CreateCameraRequest createCameraRequest, String token) {
		if (!isTokenValid(token)) {
			throw new RuntimeException("Tokenın Süresi Geçmiş");
		}
		Apartment apartment = apartmentRepository.findById(createCameraRequest.getApartmentId())
				.orElseThrow(() -> new RuntimeException("Apartman bulunamadı."));
		try {
			Camera camera = new Camera();
			camera.setCameraName(createCameraRequest.getCameraName());
			camera.setApartmentId(apartment.getApartmentId());
			this.cameraRepository.save(camera);
			return true;
		} catch (Exception e) {
			throw new RuntimeException("Camera Eklenemedi: " + e.getMessage());
		}

	}

	@Override
	public GetByIdCameraResponse getById(int id, String token) {
		Camera camera = this.cameraRepository.findById(id).orElseThrow(() -> new RuntimeException("Camera Bulanamadı"));
		Apartment apartment = apartmentRepository.findById(camera.getApartmentId()).orElse(null);
		try {
			String blokName = (apartment != null) ? apartment.getBlokName() : "Silindi";
			GetByIdCameraResponse byIdCameraResponse = new GetByIdCameraResponse();
			byIdCameraResponse.setBlokeName(blokName);
			byIdCameraResponse.setCameraName(camera.getCameraName());
			byIdCameraResponse.setId(id);
			return byIdCameraResponse;
		} catch (Exception e) {
			throw new RuntimeException("Getirme Sırasında Hata: " + e.getMessage());
		}

	}

	@Override
	public boolean update(UpdateCameraRequest updateCameraRequest, String token) {
		if (!isTokenValid(token)) {
			throw new RuntimeException("Tokenın Süresi Geçmiş");
		}
		try {
			Camera camera = cameraRepository.findById(updateCameraRequest.getId())
					.orElseThrow(() -> new RuntimeException("Camera bulunamadı."));
			Apartment apartment = apartmentRepository.findById(updateCameraRequest.getApartmentId())
					.orElseThrow(() -> new RuntimeException("Bina Bulunamadı"));
			camera.setCameraName(updateCameraRequest.getCameraName());
			camera.setApartmentId(apartment.getApartmentId());
			cameraRepository.save(camera);
			return true;
		} catch (Exception e) {
			throw new RuntimeException("Camera güncelleme işlemi sırasında bir hata oluştu: " + e.getMessage());
		}

	}

	@Override
	public boolean delete(int id, String token) {
		if (!isTokenValid(token)) {
			throw new RuntimeException("Tokenın Süresi Geçmiş");
		}
		try {
			Camera camera = cameraRepository.findById(id).orElseThrow(() -> new RuntimeException("Camera bulunamadı."));
			this.cameraRepository.deleteById(camera.getId());
			return true;
		} catch (Exception e) {
			throw new RuntimeException("Camera Ararken Hata Oluştu: " + e.getMessage());
		}

	}

}
