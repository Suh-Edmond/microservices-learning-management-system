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
public class CourseNote  extends  BaseEntity{

    @Id
    @Column(length = 60)
    private String Id;
    private String topic;
    private String file;

    @ManyToOne(fetch = FetchType.LAZY)
    private  Course course;
}
