package com.QuizApp.config.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class QuestionAttemptDTO {

	private Long questionId;
	private char userSelectedOption;
}
