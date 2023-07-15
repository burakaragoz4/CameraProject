package com.camera.API.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.camera.API.business.abstaracts.LogService;
import com.camera.API.business.requests.CreateLogRequest;
import com.camera.API.business.responses.GetAllLogsResponse;
import com.camera.API.business.responses.GetByIdLogResponse;
import com.camera.API.core.utilities.jwt.JwtUtil;
import com.camera.API.core.utilities.mappers.ModelMapperService;
import com.camera.API.dataAccess.abstracts.LogRepository;
import com.camera.API.dataAccess.abstracts.UserRepository;
import com.camera.API.entities.concretes.Log;
import com.camera.API.entities.concretes.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LogManager implements LogService {
	private LogRepository logRepository;
	private ModelMapperService modelMapperService;
	private UserRepository userRepository;
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
	public GetByIdLogResponse getById(int id, String token) {
		if (!isTokenValid(token)) {
			throw new RuntimeException("Tokenın Süresi Geçmiş");
		}
		try {
			Log log = logRepository.findById(id).orElseThrow(() -> new RuntimeException("Log Bulunamadı"));
			GetByIdLogResponse getByIdLogResponse = this.modelMapperService.forResponse().map(log,
					GetByIdLogResponse.class);
			return getByIdLogResponse;
		}catch (Exception e) {
			throw new RuntimeException("Hata: " + e.getMessage());
		}
		
	}

	@Override
	public boolean add(CreateLogRequest createLogRequest, String token) {
		if (!isTokenValid(token)) {
			throw new RuntimeException("Tokenın Süresi Geçmiş");
		}
		try {
			User user = userRepository.findById(createLogRequest.getUserId()).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));
			if(user == null) {
				// OPSİYONEL KULLANIM
				throw new RuntimeException("Tokenın Süresi Geçmiş");
			}
			Log log = this.modelMapperService.forRequest().map(createLogRequest, Log.class);
			this.logRepository.save(log);
			return true;
		} catch (Exception e) {
			throw new RuntimeException("Kaydetme Sırasında Hata " + e.getMessage());
		}

	}

	@Override
	public List<GetAllLogsResponse> getAll(String token) {
		if (!isTokenValid(token)) {
			throw new RuntimeException("Tokenın Süresi Geçmiş");
		}
		try {
			List<Log> logs = logRepository.findAll();
			if (logs == null) {
				throw new RuntimeException("LOG BULUNAMADI");
			}
			List<GetAllLogsResponse> allLogResponses = logs.stream()
					.map(log -> this.modelMapperService.forResponse().map(log, GetAllLogsResponse.class))
					.collect(Collectors.toList());
			return allLogResponses;
		} catch (Exception e) {
			throw new RuntimeException("Belirlenemeyen Hata: " + e.getMessage());
		}

	}

}
