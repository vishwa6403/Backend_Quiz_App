package com.QuizApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String questionText;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	
	private char correctOption;
	
	@ManyToOne
	@JoinColumn(name="quiz_id",nullable = true)
	private Quiz quiz;
}
