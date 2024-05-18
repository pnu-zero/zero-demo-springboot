package com.example.zero.project.domain.model;

import com.example.zero.project.domain.model.enums.ProjectRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CreateProjectRequestDto {
    private Long id;
    private final Long user_id;
    private final Long group_id;
    private final String title;
    private final String desc;
    private final String sub_domain;
    private final String static_file_src;
    private final String dynamic_file_src;
    private final ProjectRole role;
}
