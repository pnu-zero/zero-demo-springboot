package com.example.zero.project.domain.repository.mapper;

import com.example.zero.project.domain.model.ProjectDto;
import com.example.zero.group.domain.model.enums.GroupAuthority;
import com.example.zero.project.domain.model.ProjectWithUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectMapper {

    List<ProjectWithUser> getProjectsByGroupId(Long groupId);

    List<ProjectWithUser> getProjectsByUserId(Long userId);

    int selectSubDomain(String subDomain);

    Long createProject(ProjectDto projectDto);

    ProjectDetail getProjectDetail(Long id);

    int updateProjectRole(Long projectId, GroupAuthority role);

    void deleteProject(Long projectId);
}
