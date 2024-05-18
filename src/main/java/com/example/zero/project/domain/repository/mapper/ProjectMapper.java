package com.example.zero.project.domain.repository.mapper;

import com.example.zero.project.domain.model.CreateProjectRequestDto;
import com.example.zero.project.domain.model.ProjectDto;
import com.example.zero.project.domain.model.enums.ProjectRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectMapper {

    List<ProjectDto> getProjectsByGroupId(Long groupId);

    List<ProjectDto> getProjectsByUserId(Long userId);

    Long createProject(CreateProjectRequestDto projectDto);

    int updateProjectRole(Long projectId, ProjectRole role);

    void deleteProject(Long projectId);
}
