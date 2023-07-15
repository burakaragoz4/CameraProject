package com.camera.API.dataAccess.abstracts;


import org.springframework.data.jpa.repository.JpaRepository;

import com.camera.API.entities.concretes.Log;

public interface LogRepository extends JpaRepository<Log, Integer> {

}
