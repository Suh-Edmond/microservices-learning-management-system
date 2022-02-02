package com.learningmanagementsystem.CourseService.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Course  extends  BaseEntity{

    @Id
    @Column(length = 60)
    private String Id;
    @Column(unique = true)
    private String title;
    private String level;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    private String courseImage;
    private BigDecimal price;
    private boolean status;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseNote> courseNoteList;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private  List<CourseSyllabus> courseSyllabusList;
}
