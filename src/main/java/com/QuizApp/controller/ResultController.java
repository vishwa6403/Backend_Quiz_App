package com.QuizApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.QuizApp.entity.Result;
import com.QuizApp.service.ResultService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ResultController {

	@Autowired
	private ResultService resultService;
	
	@GetMapping("/generate/result")
    public ResponseEntity<ApiResponse<Result>> generateResult(@RequestParam Long userId, @RequestParam Long quizId) {
        ApiResponse<Result> response = resultService.generateResult(userId, quizId);
        return ResponseEntity.ok(response);
    }
	
}