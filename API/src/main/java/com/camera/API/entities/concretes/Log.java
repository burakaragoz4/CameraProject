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

@Table(name = "logs")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Log {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "userId")
	private int userId;

	@Column(name = "logEnterTime")
	private int logEnterTime;

	@Column(name = "logStopTime")
	private int logStopTime;

	@Column(name = "totalTime")
	private int totalTime;

	@Column(name = "logEntry")
	private int logEntry;

	@Column(name = "logExit")
	private int logExit;
}
