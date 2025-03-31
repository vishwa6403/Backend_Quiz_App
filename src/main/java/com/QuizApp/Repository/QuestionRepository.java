package com.QuizApp.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.QuizApp.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	@Query(value = "SELECT q FROM Question q WHERE q.quiz IS NULL ORDER BY FUNCTION('RAND')")
	List<Question> findRandomQuestion(Pageable pageable);
	
	List<Question> findByQuiz_Id(Long id);
	
	Optional<Question> findByIdAndQuiz_Id(Long id , Long quizId);
}
