package com.example.zero.project.service;

import com.example.zero.project.domain.model.Project;
import com.example.zero.project.domain.model.ProjectDto;
import com.example.zero.group.domain.model.enums.GroupAuthority;
import com.example.zero.project.domain.model.ProjectWithUser;
import com.example.zero.project.domain.repository.ProjectRepository;
import com.example.zero.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Transactional
    public Project createProject(ProjectDto projectDto, MultipartFile staticFile, MultipartFile dynamicFile) throws IOException {
        String generatedStaticFilePath = FileUtils.generateFilePath(projectDto, FileUtils.STATIC_FILE_DIR);
        String staticFileSrc = FileUtils.uploadStaticFile(generatedStaticFilePath, staticFile);
        String dynamicFileSrc = "";
        if (dynamicFile != null) {
            String generatedDynamicFilePath = FileUtils.generateFilePath(projectDto, FileUtils.DYNAMIC_FILE_DIR);
            dynamicFileSrc = FileUtils.uploadStaticFile(generatedDynamicFilePath, dynamicFile);
        }

        projectDto.setStatic_file_src(staticFileSrc);
        projectDto.setDynamic_file_src(dynamicFileSrc);

        ProjectDto responseProjectDto = projectRepository.createProject(projectDto);

        return Project.from(responseProjectDto);
    }

    public List<ProjectWithUser> getProjectListByGroupId(Long groupId){
        return projectRepository.getProjectListByGroupId(groupId);
    }

    public List<ProjectWithUser> getProjectListByUserId(Long userId){
        return projectRepository.getProjectListByUserId(userId);
    }

    public int updateProjectRole(Long projectId, GroupAuthority groupAuthority){
        return projectRepository.updateProjectRole(projectId, groupAuthority);
    }

    public void deleteProject(Long projectId) {
        projectRepository.deleteProject(projectId);
    }

}
