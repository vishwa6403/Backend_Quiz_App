package com.QuizApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.QuizApp.config.DTO.UserAttemptDTO;
import com.QuizApp.entity.UserAttempt;
import com.QuizApp.service.UserAttemptService;

@RestController
public class UserAttemptController {

	@Autowired
	private UserAttemptService userAttemptService;

    @PostMapping("user_attempt/submit")
    public ResponseEntity<ApiResponse<UserAttempt>> submitUserAttempt(@RequestBody UserAttemptDTO userAttemptedDTO) {
    	
        ApiResponse<UserAttempt> response = userAttemptService.submitUserAttempt(userAttemptedDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
