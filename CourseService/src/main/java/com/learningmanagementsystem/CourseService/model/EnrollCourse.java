package com.learningmanagementsystem.CourseService.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnrollCourse  extends  BaseEntity{


    @Id
    @Column(length = 60)
    private String Id;
    private String userId;
    private String courseId;
}
