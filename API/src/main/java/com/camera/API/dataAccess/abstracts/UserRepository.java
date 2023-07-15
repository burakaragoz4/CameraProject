package com.camera.API.dataAccess.abstracts;


import org.springframework.data.jpa.repository.JpaRepository;

import com.camera.API.entities.concretes.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByNameAndPassword(String name, String password);

}
