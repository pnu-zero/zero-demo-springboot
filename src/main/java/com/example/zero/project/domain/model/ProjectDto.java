package com.example.zero.project.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class ProjectDto {
    private Long id;

    private Long user_id;

    private Long group_id;

    @NotBlank(message = "프로젝트 제목은 필수 항목입니다.")
    @Size(max = 10, message = "프로젝트 제목은 최대 10자입니다.")
    private final String title;

    private final String desc;

    @NotBlank(message = "서브 도메인은 필수 항목입니다.")
    private final String sub_domain;

    private String static_file_name;

    private String static_file_src;

    private String dynamic_file_name;

    private String dynamic_file_src;
}
