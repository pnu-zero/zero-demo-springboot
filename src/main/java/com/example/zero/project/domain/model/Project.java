package com.example.zero.project.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class Project {
    private final Long id;
    private final String title;
    private final String desc;
    private final String sub_domain;

    public static Project from(ProjectDto projectDto){
        return new Project(projectDto.getId(), projectDto.getTitle(), projectDto.getDesc(), projectDto.getSub_domain());
    }
}
