package com.learningmanagementsystem.QuestionsAndAnswersService.repository;

import com.learningmanagementsystem.QuestionsAndAnswersService.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
}
