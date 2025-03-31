package com.QuizApp.config.DTO;

import java.util.List;

import lombok.Data;

@Data
public class UserAttemptDTO {
	private Long userId;
	private Long quizId;
	private List<QuestionAttemptDTO> attempts;
}
