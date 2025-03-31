package com.QuizApp.controller;

import com.QuizApp.entity.Quiz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {


	public ApiResponse(int code, String status, String message, T data) {
		this.code = code;
		this.status = status;
		this.message = message;
		this.data = data;
	}
	private int code;
	private String status;
	private String message;
	private T data;
}
