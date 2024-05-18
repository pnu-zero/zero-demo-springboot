package com.example.zero.project.domain.repository.mapper;

import com.example.zero.project.domain.model.ProjectDto;
import com.example.zero.group.domain.model.enums.GroupAuthority;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectMapper {

    List<ProjectDto> getProjectsByGroupId(Long groupId);

    List<ProjectDto> getProjectsByUserId(Long userId);

    boolean selectSubDomain(String subDomain);

    Long createProject(ProjectDto projectDto);

    int updateProjectRole(Long projectId, GroupAuthority role);

    void deleteProject(Long projectId);
}
