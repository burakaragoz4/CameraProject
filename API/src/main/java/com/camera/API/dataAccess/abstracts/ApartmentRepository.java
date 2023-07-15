package com.camera.API.dataAccess.abstracts;


import org.springframework.data.jpa.repository.JpaRepository;

import com.camera.API.entities.concretes.Apartment;

public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {

	
}
