package com.learningmanagementsystem.QuestionsAndAnswersService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question  extends  BaseEntity{

    @Id
    @Column(length = 50)
    private String Id;
    private String courseId;
    private String studentId;
    @Column(nullable = true)
    private String image;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String subject;
    @Column(columnDefinition = "LONGTEXT")
    private String details;
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, orphanRemoval=true)
    private List<Answer> answers;
}
