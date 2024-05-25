package com.example.zero.project.domain.repository.mapper;

import com.example.zero.project.domain.model.ProjectDetail;
import com.example.zero.project.domain.model.ProjectDto;
import com.example.zero.group.domain.model.enums.GroupAuthority;
import com.example.zero.project.domain.model.ProjectWithUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectMapper {

    List<ProjectWithUser> getProjectsByGroupId(Long groupId);

    List<ProjectWithUser> getProjectsByUserId(Long userId);

    List<ProjectWithUser> searchProjects(String query);

    int selectSubDomain(String subDomain);

    String getUserSubDomain(Long userId, Long groupId);

    Long createProject(ProjectDto projectDto);

    ProjectDetail getProjectDetail(Long userId, Long groupId);

    int updateProjectRole(Long projectId, GroupAuthority role);

    void deleteProject(Long projectId);
}
