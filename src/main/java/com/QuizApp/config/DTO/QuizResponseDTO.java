package com.QuizApp.config.DTO;

import java.util.List;
import java.util.stream.Collectors;

import com.QuizApp.entity.Quiz;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizResponseDTO {
	private Long id;
    private String quizName;
    
    public QuizResponseDTO(Quiz quiz) {
        this.id = quiz.getId();
        this.quizName = quiz.getQuizName();
    }
}
