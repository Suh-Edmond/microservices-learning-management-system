package com.learningmanagementsystem.QuestionsAndAnswersService.repository;

import com.learningmanagementsystem.QuestionsAndAnswersService.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, String> {

    List<Answer> findAnswersByQuestion(String questionId);
    List<Answer> findByReplierId(String replierId);
}
