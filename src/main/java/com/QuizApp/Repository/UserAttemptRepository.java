package com.QuizApp.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.QuizApp.entity.UserAttempt;

public interface UserAttemptRepository extends JpaRepository<UserAttempt, Long> {

	List<UserAttempt> findByUserId(Long userId);
	
	List<UserAttempt> findByQuizId(Long quizId);
	
	Optional<UserAttempt> findByUserIdAndQuizId(Long userId,Long quizId);
}
