package com.camera.API.dataAccess.abstracts;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camera.API.entities.concretes.Camera;

public interface CameraRepository extends JpaRepository<Camera, Integer> {

	List<Camera> findAllByApartmentId(int id);
}
