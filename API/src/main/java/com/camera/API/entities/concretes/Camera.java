package com.camera.API.entities.concretes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "cameras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Camera {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "cameraName")
	private String cameraName;

	@Column(name = "apartmentId")
	private int apartmentId;
}
