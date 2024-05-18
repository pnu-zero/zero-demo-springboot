package com.example.zero.project.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProjectWithUser {
    private final Long id;
    private final String title;
    private final String desc;
    private final String sub_domain;
    private final String student_id;
    private final String name;
}
