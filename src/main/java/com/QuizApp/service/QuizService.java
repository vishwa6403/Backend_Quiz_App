package com.QuizApp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.QuizApp.Repository.QuestionRepository;
import com.QuizApp.Repository.QuizRepository;
import com.QuizApp.config.DTO.QuizQuestionDTO;
import com.QuizApp.config.DTO.QuizResponseDTO;
import com.QuizApp.entity.Question;
import com.QuizApp.entity.Quiz;

import jakarta.persistence.EntityNotFoundException;

@Service
public class QuizService {

	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	//Fetch All quizzes
	 public List<QuizResponseDTO> getAllQuizzes() {
	        List<Quiz> quizzes = quizRepository.findAll();
	        return (quizzes.stream()
	                .map(QuizResponseDTO::new)
	                .collect(Collectors.toList()));
	    }
	 
	//Fetch quiz by id
	public QuizResponseDTO getQuizById(Long id) {
		Optional<Quiz> quiz = quizRepository.findById(id);
		return quiz.map(QuizResponseDTO::new).orElse(null);
	}
	
	//assign quiz random questions
//	public Quiz createQuizWithRandomQuestions(String quizName) {
//		
//		Quiz existingQuiz = quizRepository.findByQuizName(quizName);
//		
//		if(existingQuiz!= null) {
//			throw new IllegalArgumentException("Quiz already exists with : "+ quizName);
//		}
//		List<Question> randomQuestions = questionRepository.findRandomQuestion();
//		
//		Quiz quiz = new Quiz();
//		quiz.setQuizName(quizName);
//		quiz.setQuestions(randomQuestions);
//		
//		for(Question question: randomQuestions) {
//			question.setQuiz(quiz);
//		}
//		quiz.setQuestions(randomQuestions);
//		
//		return quizRepository.save(quiz);
//		
//	}
	public Quiz createQuizWithRandomQuestions(String quizName, int questionLimit) {
        // ❌ Check if quiz name already exists
        Quiz existingQuiz = quizRepository.findByQuizName(quizName);
        if (existingQuiz!=null) {
            throw new IllegalArgumentException("Quiz already created with this name!");
        }

        // 🔍 Fetch random unassigned questions using Pageable
        Pageable pageable = PageRequest.of(0, questionLimit);
        List<Question> availableQuestions = questionRepository.findRandomQuestion(pageable);

        // ❌ Prevent quiz creation if not enough questions are available
        if (availableQuestions.size() < questionLimit) {
            throw new IllegalArgumentException("Not enough unassigned questions available! Only "
                + availableQuestions.size() + " left.");
        }

        // 🏆 Create new quiz
        Quiz quiz = new Quiz();
        quiz.setQuizName(quizName);

        // 🔗 Assign questions to quiz
        for (Question question : availableQuestions) {
            question.setQuiz(quiz);
        }
        quiz.setQuestions(availableQuestions);

        // 💾 Save and return
        return quizRepository.save(quiz);
    }
}
