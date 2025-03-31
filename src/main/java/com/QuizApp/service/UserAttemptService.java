package com.QuizApp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.QuizApp.Repository.QuestionRepository;
import com.QuizApp.Repository.UserAttemptRepository;
import com.QuizApp.config.DTO.QuestionAttemptDTO;
import com.QuizApp.config.DTO.UserAttemptDTO;
import com.QuizApp.controller.ApiResponse;
import com.QuizApp.entity.Question;
import com.QuizApp.entity.UserAttempt;

@Service
public class UserAttemptService {

	@Autowired
	private UserAttemptRepository userAttemptRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	  public ApiResponse<UserAttempt> submitUserAttempt(UserAttemptDTO userAttemptedDTO) {
	        // Check if the user has already attempted this quiz
	        Optional<UserAttempt> existingAttempt = userAttemptRepository.findByUserIdAndQuizId(
	                userAttemptedDTO.getUserId(), userAttemptedDTO.getQuizId());

	        if (existingAttempt.isPresent()) {
	            return new ApiResponse<>(
	                    HttpStatus.CONFLICT.value(),
	                    "ERROR",
	                    "User has already attempted this quiz!",
	                    null
	            );
	        }

	        // Create a new UserAttempt
	        UserAttempt userAttempt = new UserAttempt();
	        userAttempt.setUserId(userAttemptedDTO.getUserId());
	        userAttempt.setQuizId(userAttemptedDTO.getQuizId());
	        userAttempt.setStartTime(LocalDateTime.now());

	        List<Long> questionId = new ArrayList<>();
	        List<String> userSelectedOption = new ArrayList<>();

	        for (QuestionAttemptDTO attempt : userAttemptedDTO.getAttempts()) {
	            // Validate if the question belongs to the quiz
	            Optional<Question> question = questionRepository.findByIdAndQuiz_Id(
	                    attempt.getQuestionId(), userAttemptedDTO.getQuizId());

	            if (question.isEmpty()) {
	                return new ApiResponse<>(
	                        HttpStatus.BAD_REQUEST.value(),
	                        "BAD_REQUEST",
	                        "Question ID " + attempt.getQuestionId() + " does not belong to quiz ID " + userAttemptedDTO.getQuizId(),
	                        null
	                );
	            }

	            questionId.add(attempt.getQuestionId());
	            userSelectedOption.add(String.valueOf(attempt.getUserSelectedOption()));
	        }

	        userAttempt.setQuestionId(questionId);
	        userAttempt.setUserAttemptedOption(userSelectedOption);
	        userAttempt.setAttempted(true);
	        userAttempt.setEndTime(LocalDateTime.now());

	        UserAttempt savedAttempt = userAttemptRepository.save(userAttempt);
	        return new ApiResponse<>(
	                HttpStatus.OK.value(),
	                "OK",
	                "User attempt submitted successfully!",
	                savedAttempt
	        );
	    }

}
