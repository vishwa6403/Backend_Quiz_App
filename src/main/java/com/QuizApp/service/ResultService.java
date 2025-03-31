package com.QuizApp.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.QuizApp.Repository.QuestionRepository;
import com.QuizApp.Repository.ResultRepository;
import com.QuizApp.Repository.UserAttemptRepository;
import com.QuizApp.controller.ApiResponse;
import com.QuizApp.entity.Question;
import com.QuizApp.entity.Result;
import com.QuizApp.entity.UserAttempt;

@Service
public class ResultService {

	@Autowired
	private ResultRepository resultRepository;
	@Autowired
	private UserAttemptService userAttemptService;
	@Autowired
	private UserAttemptRepository userAttemptRepository;
	@Autowired
	private QuestionRepository questionRepository;
	
//	public QuizResultDTO calculateAndSaveResult(String userId , long quizId) {
//		UserAttempt userAttempts = userAttemptRepository.findByUserIdAndQuizId(userId, quizId);
//		List<Question> questions = questionRepository.findByQuiz_Id(quizId);
//		
//		int totalQuestions = questions.size();
//		int totalAttempted = userAttempts.size();
//		int totalCorrect = 0;
//		
//		for(UserAttempt attempt : userAttempts) {
//			Question question = questions.stream()
//					.filter(q-> q.getId().equals(attempt.getQuestionId()))
//					.findFirst()
//					.orElseThrow();
//			
////			if(attempt.getUserAttemptedOption() == question.getCorrectOption()) {
////				totalCorrect++;
////			}
//		}
//		int totalIncorrect = totalAttempted - totalCorrect;
//		int score = totalCorrect ;
//		
//		return new QuizResultDTO(userId,quizId , totalQuestions,totalAttempted,totalCorrect,totalIncorrect,score);
//		
//
//	}
	
	   /**
     * Generates and saves the quiz result when a user submits an attempt.
     */
    public ApiResponse<Result> generateResult(Long userId, Long quizId) {
        // Fetch the user's attempt for the quiz
        UserAttempt userAttempt = userAttemptRepository.findByUserIdAndQuizId(userId, quizId)
                .orElseThrow(() -> new NoSuchElementException("No attempt found for user " + userId + " and quiz " + quizId));

        // Fetch all questions for this quiz
        List<Long> questionIds = userAttempt.getQuestionId();
        List<String> userAnswers = userAttempt.getUserAttemptedOption();

        if (questionIds.isEmpty()) {
            return new ApiResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "BAD_REQUEST",
                    "No questions found in attempt",
                    null
            );
        }

        int correctCount = 0;
        int incorrectCount = 0;

        // Checking user's selected options against correct answers
        for (int i = 0; i < questionIds.size(); i++) {
            Long questionId = questionIds.get(i);
            char userAnswer = userAnswers.get(i).charAt(0);

            Question question = questionRepository.findByIdAndQuiz_Id(questionId, quizId)
                    .orElseThrow(() -> new NoSuchElementException("Question not found with ID: " + questionId));

            if (question.getCorrectOption() == userAnswer) {
                correctCount++;
            } else {
                incorrectCount++;
            }
        }

        // Calculate the score (Assume each correct answer gives 5 points)
        int score = correctCount * 5;

        // Save the result
        Result result = new Result();
        result.setUser_id(userId);
        result.setQuiz_id(quizId);
        result.setTotalCorrect(correctCount);
        result.setTotalIncorrect(incorrectCount);
        result.setScore(score);

        resultRepository.save(result);

        return new ApiResponse<>(HttpStatus.OK.value(),"OK", "Result generated successfully", result);
    }
}
