package com.learningmanagementsystem.QuestionsAndAnswersService.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Answer extends BaseEntity{

    @Id
    @Column(length = 50)
    private String Id;
    @Column(columnDefinition = "LONGTEXT")
    private String response;
    @Column(columnDefinition = "LONGTEXT")
    private String details;
    private String image;
    private String replierId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

}
