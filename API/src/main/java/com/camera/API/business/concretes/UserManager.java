package com.camera.API.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.camera.API.business.abstaracts.UserService;
import com.camera.API.business.requests.CreateUserRequest;
import com.camera.API.business.requests.UpdateUserRequest;
import com.camera.API.business.responses.GetAllUsersResponse;
import com.camera.API.business.responses.GetByIdUserResponse;
import com.camera.API.business.responses.LoginResponse;
import com.camera.API.core.utilities.jwt.JwtUtil;
import com.camera.API.dataAccess.abstracts.ApartmentRepository;
import com.camera.API.dataAccess.abstracts.CameraRepository;
import com.camera.API.dataAccess.abstracts.UserRepository;
import com.camera.API.entities.concretes.Apartment;
import com.camera.API.entities.concretes.Camera;
import com.camera.API.entities.concretes.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserManager implements UserService {
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final ApartmentRepository apartmentRepository;
	private final CameraRepository cameraRepository;

	public boolean isTokenValid(String token) {
		try {
			String username = jwtUtil.getUsernameFromToken(token);
			return username != null && jwtUtil.isTokenValidForUser(token, username);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<GetAllUsersResponse> getAll(String token) {
		if (!isTokenValid(token)) {
			throw new RuntimeException("Tokenın Süresi Geçmiş");
		}
		try {
			List<User> users = userRepository.findAll();
			if(users == null) {
				throw new RuntimeException("Kullanıcı Bulanamadı");
			}
			List<GetAllUsersResponse> usersResponse = new ArrayList<>();

			for (User user : users) {
				GetAllUsersResponse response = new GetAllUsersResponse();
				Apartment apartment = apartmentRepository.findById(user.getApartmentId()).orElse(null);
				if (apartment == null) {
					response.setId(user.getId());
					response.setName(user.getName());
					response.setPassword(user.getPassword());
					response.setBlokName("SİLİNDİ");
					response.setDaireNo(user.getDaireNo());
					usersResponse.add(response);
				} else {
					response.setId(user.getId());
					response.setName(user.getName());
					response.setPassword(user.getPassword());
					response.setBlokName(apartment.getBlokName());
					response.setDaireNo(user.getDaireNo());
					usersResponse.add(response);
				}

			}

			return usersResponse;
		} catch (Exception e) {
			throw new RuntimeException("Getirme Sırasında Hata Ortaya Çıktı: " + e.getMessage());
		}

	}

	@Override
	public boolean add(CreateUserRequest createUserRequest) {
		Apartment apartment = apartmentRepository.findById(createUserRequest.getApartmentId())
				.orElseThrow(() -> new RuntimeException("Apartman bulunamadı."));
		try {
			User user = new User();
			user.setApartmentId(createUserRequest.getApartmentId());
			user.setDaireNo(apartment.getApartmentId());
			user.setName(createUserRequest.getName());
			user.setPassword(createUserRequest.getPassword());
			userRepository.save(user);
			return true;
		} catch (Exception e) {
			throw new RuntimeException("Kullanıcı Eklenemedi" + e.getMessage());
		}

	}

	@Override
	public GetByIdUserResponse getById(int id, String token) {
		if (!isTokenValid(token)) {
			throw new RuntimeException("Tokenın Süresi Geçmiş");
		}

		try {
			User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));
			Apartment apartment = apartmentRepository.findById(user.getApartmentId()).orElse(null);
			String blokName = (apartment != null) ? apartment.getBlokName() : "";
			List<Camera> cameras = cameraRepository.findAllByApartmentId(user.getApartmentId());
			GetByIdUserResponse response = new GetByIdUserResponse();
			response.setName(user.getName());
			response.setId(user.getId());
			response.setBlokeName(blokName);
			response.setCameras(cameras);
			response.setDaireNo(user.getDaireNo());
			return response;
		} catch (Exception e) {
			throw new RuntimeException("Kullanıcı Bulunamadı " + e.getMessage());
		}

	}

	@Override
	public boolean update(UpdateUserRequest updateUserRequest, String token) {
		if (!isTokenValid(token)) {
			throw new RuntimeException("Tokenın Süresi Geçmiş");
		}
		try {
			User user = userRepository.findById(updateUserRequest.getId())
					.orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));
			Apartment apartment = apartmentRepository.findById(updateUserRequest.getApartmentId()).orElseThrow(() -> new RuntimeException("Apartman Bulunamadı"));
			user.setName(updateUserRequest.getName());
			user.setPassword(updateUserRequest.getPassword());
			user.setDaireNo(updateUserRequest.getDaireNo());
			user.setApartmentId(apartment.getApartmentId());
			userRepository.save(user);
			return true;
		} catch (Exception e) {
			throw new RuntimeException("Kullanıcı güncelleme işlemi sırasında bir hata oluştu: " + e.getMessage());
		}
	}

	@Override
	public boolean delete(int id, String token) {
		if (!isTokenValid(token)) {
			throw new RuntimeException("Tokenın Süresi Geçmiş");
		}
		try {
			User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));
			userRepository.deleteById(user.getId());
			return true;
		} catch (Exception e) {
			throw new RuntimeException("Kayıt Silinemedi" + e.getMessage());
		}

	}

	@Override
	public LoginResponse login(String name, String password) {
		try {
			String token = "";
			User user = userRepository.findByNameAndPassword(name, password);
			if (user != null) {
				token = jwtUtil.generateToken(user);
				Apartment apartment = apartmentRepository.findById(user.getApartmentId()).orElse(null);
				String blokName = (apartment != null) ? apartment.getBlokName() : "";

				List<Camera> cameras = cameraRepository.findAllByApartmentId(user.getApartmentId());

				if (cameras.isEmpty()) {
					throw new RuntimeException("Verilen apartman için kamera bulunamadı.");
				}
				LoginResponse loginResponse = new LoginResponse();
				loginResponse.setUserName(user.getName());
				loginResponse.setPassword(user.getPassword());
				loginResponse.setToken(token);
				loginResponse.setBlokName(blokName);
				loginResponse.setChannelNames(cameras);
				return loginResponse;
			} else {
				throw new RuntimeException("Geçersiz kimlik bilgileri. Kullanıcı bulunamadı.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Giriş sırasında bir hata oluştu: " + e.getMessage());
		}
	}

}
