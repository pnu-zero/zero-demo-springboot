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

    @NotBlank(message = "그룹 아이디는 필수 항목입니다.")
    private final Long user_id;

    @NotBlank(message = "그룹 아이디는 필수 항목입니다.")
    private final Long group_id;

    @NotBlank(message = "프로젝트 제목은 필수 항목입니다.")
    @Size(max = 10, message = "프로젝트 제목은 최대 10자입니다.")
    private final String title;

    private final String desc;

    @NotBlank(message = "서브 도메인은 필수 항목입니다.")
    private final String sub_domain;

    public static ProjectDto fromCreateProjectDto(CreateProjectRequestDto createProjectRequestDto) {
        ProjectDto projectDto = new ProjectDto(
                createProjectRequestDto.getUser_id(),
                createProjectRequestDto.getGroup_id(),
                createProjectRequestDto.getTitle(),
                createProjectRequestDto.getDesc(),
                createProjectRequestDto.getSub_domain());
        projectDto.setId(createProjectRequestDto.getId());
        return projectDto;
    }
}
