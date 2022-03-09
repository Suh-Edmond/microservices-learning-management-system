package com.learningmanagementsystem.CourseService.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseSyllabus extends BaseEntity {

    @Id
    @Column(length = 60)
    private String Id;
    @ManyToOne(fetch = FetchType.LAZY)
    private  Course course;
    private String file;
}
