package com.QuizApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.QuizApp.entity.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {

}
