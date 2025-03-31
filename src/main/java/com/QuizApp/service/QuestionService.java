package com.QuizApp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QuizApp.Repository.QuestionRepository;
import com.QuizApp.config.DTO.QuestionResponseDTO;
import com.QuizApp.config.DTO.QuizQuestionDTO;
import com.QuizApp.entity.Question;
import com.QuizApp.entity.Quiz;

import jakarta.persistence.EntityNotFoundException;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	public void createQuestion(List<Question> ques) {
		questionRepository.saveAll(ques);
	}
	
	public List<QuestionResponseDTO> getAllQuestions() {
		List<Question> questions = questionRepository.findAll();
		return questions.stream().map(QuestionResponseDTO::new).collect(Collectors.toList());
	}

	public List<QuestionResponseDTO> getQuestionsByQuizId(Long quizId) {
		List<Question> questions = questionRepository.findByQuiz_Id(quizId);
		return questions.stream().map(QuestionResponseDTO::new).collect(Collectors.toList());
	}
	
    // Fetch a specific question by quiz ID and question ID
    public QuestionResponseDTO getQuestionByIdAndQuizId(Long quizId, Long questionId) {
        Optional<Question> questionOptional = questionRepository.findByIdAndQuiz_Id(questionId, quizId);
        return questionOptional.map(QuestionResponseDTO::new).orElse(null);
    }

    public QuestionResponseDTO updateQuestion(Long quizId, Long questionId, Question updatedQuestion) {

        // Check if Question exists and belongs to the Quiz
        Question existingQuestion = questionRepository.findByIdAndQuiz_Id(questionId,quizId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found with ID: " + questionId));

        // Update question details
        if (updatedQuestion.getQuestionText() != null && !updatedQuestion.getQuestionText().isBlank()) {
            existingQuestion.setQuestionText(updatedQuestion.getQuestionText());
        }
        if (updatedQuestion.getOptionA() != null && !updatedQuestion.getOptionA().isBlank()) {
            existingQuestion.setOptionA(updatedQuestion.getOptionA());
        }
        if (updatedQuestion.getOptionB() != null && !updatedQuestion.getOptionB().isBlank()) {
            existingQuestion.setOptionB(updatedQuestion.getOptionB());
        }
        if (updatedQuestion.getOptionC() != null && !updatedQuestion.getOptionC().isBlank()) {
            existingQuestion.setOptionC(updatedQuestion.getOptionC());
        }
        if (updatedQuestion.getOptionD() != null && !updatedQuestion.getOptionD().isBlank()) {
            existingQuestion.setOptionD(updatedQuestion.getOptionD());
        }
        if (updatedQuestion.getCorrectOption() != '\0') { // Ensures the correct option is provided
            existingQuestion.setCorrectOption(updatedQuestion.getCorrectOption());
        }

        // Save and return updated question
        Question savedQuestion = questionRepository.save(existingQuestion);
        return convertToDTO(savedQuestion);
    }
    private QuestionResponseDTO convertToDTO(Question question) {
    	QuestionResponseDTO dto = new QuestionResponseDTO(question);
        dto.setId(question.getId());
        dto.setQuestionText(question.getQuestionText());
        dto.setOptionA(question.getOptionA());
        dto.setOptionB(question.getOptionB());
        dto.setOptionC(question.getOptionC());
        dto.setOptionD(question.getOptionD());
        dto.setCorrectOption(question.getCorrectOption());
        return dto;
    }
}
