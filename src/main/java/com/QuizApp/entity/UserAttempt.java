package com.QuizApp.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UserAttempt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long userId;
	private Long quizId;
	
	@ElementCollection
	private List<Long> questionId;
	@ElementCollection
	private List<String> userAttemptedOption;
	
	private boolean isAttempted;
	
	private LocalDateTime startTime;
	private LocalDateTime endTime;
}
