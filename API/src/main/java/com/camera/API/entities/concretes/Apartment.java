package com.camera.API.entities.concretes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "apartments")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Apartment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "apartmentId")
	private int apartmentId;

	@NotNull
	@NotBlank
	@Column(name = "blokName")
	private String blokName;

}
