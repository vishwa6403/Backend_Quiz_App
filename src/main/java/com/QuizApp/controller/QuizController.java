package com.QuizApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.QuizApp.config.DTO.QuizQuestionDTO;
import com.QuizApp.config.DTO.QuizResponseDTO;
import com.QuizApp.entity.Quiz;
import com.QuizApp.service.QuizService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	private QuizService quizService;

	 // ✅ Add quiz
	@PostMapping("/admin/addQuiz")
	public ResponseEntity<?> createQuiz(@RequestParam String quizName, @RequestParam int questionLimit) {
		try {
			  Quiz quiz = quizService.createQuizWithRandomQuestions(quizName, questionLimit);
			ApiResponse<Quiz> response = new ApiResponse<Quiz>(HttpStatus.OK.value(), "OK",
					"Quiz created successfully!", null);
			return ResponseEntity.ok(response);

		} catch (IllegalArgumentException e) {

			ApiResponse<Quiz> errorResponse = new ApiResponse<>(HttpStatus.CONFLICT.value(), "CONFLICT",
					"Quiz already exists with name : " + quizName, null);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);

		} catch (Exception e) {

			ApiResponse<?> errorResponse = new ApiResponse<Object>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"INTERNAL_SERVER_ERROR", "An error occurred while creating quiz", null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}

	}

	 // ✅ Get list of quizzes
	@GetMapping("/admin/getAllQuiz")
	public ResponseEntity<ApiResponse<List<QuizResponseDTO>>> getAllQuizzes() {
			List<QuizResponseDTO> quizzes = quizService.getAllQuizzes();

			ApiResponse<List<QuizResponseDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), "OK",
					"All quizzes retrieved successfully!", quizzes);

			return ResponseEntity.ok(response);
	}

	 // ✅ Get a specific quiz by quiz ID 
	@GetMapping("/{id}")
	public ResponseEntity<?> getQuizById(@PathVariable Long id) {
		QuizResponseDTO quizResponse = quizService.getQuizById(id);

        if (quizResponse == null) {
            ApiResponse<?> response = new ApiResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    "NOT_FOUND",
                    "Quiz not found with ID: " + id,
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<QuizResponseDTO> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "OK",
                "Quiz retrieved successfully!",
                quizResponse
        );

        return ResponseEntity.ok(response);
	}

}
