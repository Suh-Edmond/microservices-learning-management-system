package com.learningmanagementsystem.QuestionsAndAnswersService.repository;

import com.learningmanagementsystem.QuestionsAndAnswersService.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {

    List<Question> findByCourseId(String courseId);
}
