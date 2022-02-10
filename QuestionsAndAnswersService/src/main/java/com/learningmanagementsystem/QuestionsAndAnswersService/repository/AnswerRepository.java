package com.learningmanagementsystem.QuestionsAndAnswersService.repository;

import com.learningmanagementsystem.QuestionsAndAnswersService.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, String> {
}
