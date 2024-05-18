package com.example.zero.project.domain.repository;

import com.example.zero.project.domain.model.ProjectDto;
import com.example.zero.group.domain.model.enums.GroupAuthority;
import com.example.zero.project.domain.model.ProjectWithUser;
import com.example.zero.project.domain.repository.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectRepository {
    private final ProjectMapper projectMapper;

    public List<ProjectWithUser> getProjectListByGroupId(Long groupId) {
        return projectMapper.getProjectsByGroupId(groupId);
    }

    public List<ProjectWithUser> getProjectListByUserId(Long userId) {
        return projectMapper.getProjectsByUserId(userId);
    }

    public ProjectDto createProject(ProjectDto projectDto) {
        projectMapper.createProject(projectDto);
        return projectDto;
    }

    public boolean isDuplicateSubdomainExist(String subDomain){
        return projectMapper.selectSubDomain(subDomain);
    }

    public int updateProjectRole(Long projectId, GroupAuthority role) {
        return projectMapper.updateProjectRole(projectId, role);
    }

    public void deleteProject(Long projectId) {
        projectMapper.deleteProject(projectId);
    }
}
