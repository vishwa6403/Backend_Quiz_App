package com.QuizApp.config.DTO;

import java.util.List;
import java.util.stream.Collectors;

import com.QuizApp.entity.Quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizQuestionDTO {
	private Long id;
    private String quizName;
    private List<QuestionResponseDTO> questions;

    public QuizQuestionDTO(Quiz quiz) {
        this.id = quiz.getId();
        this.quizName = quiz.getQuizName();
        this.questions = quiz.getQuestions().stream()
                .map(QuestionResponseDTO::new)
                .collect(Collectors.toList());
    }
}
