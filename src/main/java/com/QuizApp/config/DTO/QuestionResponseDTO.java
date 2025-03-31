package com.QuizApp.config.DTO;

import com.QuizApp.entity.Question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class QuestionResponseDTO {
	private Long id;

	private String questionText;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;

	private char correctOption;
	
	public QuestionResponseDTO(Question question) {
		this.id=question.getId();
		this.questionText=question.getQuestionText();
		this.optionA = question.getOptionA();
		this.optionB = question.getOptionB();
		this.optionC = question.getOptionC();
		this.optionD = question.getOptionD();
		this.correctOption = question.getCorrectOption();
	}
}
