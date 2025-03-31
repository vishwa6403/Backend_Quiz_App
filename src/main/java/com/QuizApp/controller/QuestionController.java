package com.QuizApp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.QuizApp.config.DTO.QuestionResponseDTO;
import com.QuizApp.config.DTO.QuizQuestionDTO;
import com.QuizApp.entity.Question;
import com.QuizApp.entity.Quiz;
import com.QuizApp.service.QuestionService;

@RestController
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	 // ✅ Add list of questions
	@PostMapping("/addQuestion")
	public ResponseEntity<ApiResponse> createQuestion(@RequestBody List<Question> questions) {
		
		try {
            if (questions == null || questions.isEmpty()) {
             
            	ApiResponse response = new ApiResponse();
            	response.setCode(HttpStatus.BAD_REQUEST.value());
            	response.setStatus("BAD_REQUEST");
            	response.setMessage("Question list is empty or invalid");
            	
            	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(response);
            }

            questionService.createQuestion(questions);

            ApiResponse response = new ApiResponse();
            response.setCode(HttpStatus.OK.value());
            response.setStatus("OK");
            response.setMessage("Questions added successfully!");
            
        	return ResponseEntity.status(HttpStatus.OK)
                    .body(response);
        	
        } catch (Exception e) {
        	
            ApiResponse response = new ApiResponse();
        	response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        	response.setStatus("INTERNAL_SERVER_ERROR");
        	response.setMessage("An error occurred while adding questions");
        	
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }
	
	 // ✅ Get all question 
	@GetMapping("/getAllQuestions")
	public ResponseEntity<ApiResponse<List<QuestionResponseDTO>>> getQuestions() {
		List<QuestionResponseDTO> quizzes = questionService.getAllQuestions();

		ApiResponse<List<QuestionResponseDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), "OK",
				"All questions retrieved successfully!", quizzes);

		return ResponseEntity.ok(response);
	}
	
	   // ✅ Get all questions for a specific quiz ID
    @GetMapping("/quizId")
    public ResponseEntity<ApiResponse<List<QuestionResponseDTO>>> getQuestionsByQuizId(@RequestParam Long quizId) {
        List<QuestionResponseDTO> questions = questionService.getQuestionsByQuizId(quizId);

        if (questions.isEmpty()) {
            ApiResponse<List<QuestionResponseDTO>> response = new ApiResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    "NOT_FOUND",
                    "No questions found for Quiz ID: " + quizId,
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<List<QuestionResponseDTO>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "OK",
                "Questions retrieved successfully!",
                questions
        );

        return ResponseEntity.ok(response);
    }
    
    // ✅ Get a specific question by quiz ID and question ID
    @GetMapping("/quizId/questionId")
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> getQuestionByIdAndQuizId(
            @RequestParam Long quizId, @RequestParam Long questionId) {
    	QuestionResponseDTO question = questionService.getQuestionByIdAndQuizId(quizId, questionId);

        if (question == null) {
            ApiResponse<QuestionResponseDTO> response = new ApiResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    "NOT_FOUND",
                    "Question not found with ID: " + questionId + " in Quiz ID: " + quizId,
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<QuestionResponseDTO> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "OK",
                "Question retrieved successfully!",
                question
        );

        return ResponseEntity.ok(response);
    }
	
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> updateQuestion(
            @RequestParam Long quizId,
            @RequestParam Long questionId,
            @RequestBody Question updatedQuestion) {
        try {
        	QuestionResponseDTO question = questionService.updateQuestion(quizId, questionId, updatedQuestion);
            ApiResponse response = new ApiResponse(
                    HttpStatus.OK.value(),
                    "OK",
                    "Question updated successfully",
                    question
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse response = new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "ERROR",
                    e.getMessage(),
                    null
            );
            return ResponseEntity.badRequest().body(response);
        }
    }

}
