package com.example.zero.project.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class ProjectDetail {
    private final Long id;
    private final String title;
    private final String desc;
    private final String sub_domain;
    private final String static_file_name;
    private final String dynamic_file_name;
}
