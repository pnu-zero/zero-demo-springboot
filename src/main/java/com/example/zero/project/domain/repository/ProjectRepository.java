package com.example.zero.project.domain.repository;

import com.example.zero.project.domain.model.CreateProjectRequestDto;
import com.example.zero.project.domain.model.ProjectDto;
import com.example.zero.project.domain.model.enums.ProjectRole;
import com.example.zero.project.domain.repository.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectRepository {
    private final ProjectMapper projectMapper;

    public List<ProjectDto> getProjectListByGroupId(Long groupId) {
        return projectMapper.getProjectsByGroupId(groupId);
    }

    public List<ProjectDto> getProjectListByUserId(Long userId) {
        return projectMapper.getProjectsByUserId(userId);
    }

    public ProjectDto createProject(CreateProjectRequestDto createProjectRequestDto) {
        projectMapper.createProject(createProjectRequestDto);
        return ProjectDto.fromCreateProjectDto(createProjectRequestDto);
    }

    public int updateProjectRole(Long projectId, ProjectRole role) {
        return projectMapper.updateProjectRole(projectId, role);
    }

    public void deleteProject(Long projectId) {
        projectMapper.deleteProject(projectId);
    }
}
