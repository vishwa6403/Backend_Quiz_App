package com.QuizApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.QuizApp.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz,Long> {

	Quiz findByQuizName(String quizName);
}
