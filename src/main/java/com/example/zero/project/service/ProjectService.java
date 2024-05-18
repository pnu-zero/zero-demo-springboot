package com.example.zero.project.service;

import com.example.zero.project.domain.model.CreateProjectRequestDto;
import com.example.zero.project.domain.model.ProjectDto;
import com.example.zero.project.domain.model.enums.ProjectRole;
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
    public ProjectDto createProject(ProjectDto projectDto, MultipartFile staticFile, MultipartFile dynamicFile) throws IOException {

        String generatedStaticFilePath = FileUtils.generateFilePath(projectDto, FileUtils.STATIC_FILE_DIR);
        String staticFileSrc = FileUtils.uploadStaticFile(generatedStaticFilePath, staticFile);
        String dynamicFileSrc = "";
        if (dynamicFile != null) {
            String generatedDynamicFilePath = FileUtils.generateFilePath(projectDto, FileUtils.DYNAMIC_FILE_DIR);
            dynamicFileSrc = FileUtils.uploadStaticFile(generatedDynamicFilePath, dynamicFile);
        }

        return projectRepository.createProject(CreateProjectRequestDto.builder()
                        .user_id(projectDto.getUser_id())
                        .group_id(projectDto.getGroup_id())
                        .title(projectDto.getTitle())
                        .desc(projectDto.getDesc())
                        .sub_domain(projectDto.getSub_domain())
                        .static_file_src(staticFileSrc)
                        .dynamic_file_src(dynamicFileSrc)
                        .role(ProjectRole.Private)
                        .build());
    }

    public List<ProjectDto> getProjectListByGroupId(Long groupId){
        return projectRepository.getProjectListByGroupId(groupId);
    }

    public List<ProjectDto> getProjectListByUserId(Long userId){
        return projectRepository.getProjectListByUserId(userId);
    }

    public int updateProjectRole(Long projectId, ProjectRole projectRole){
        return projectRepository.updateProjectRole(projectId, projectRole);
    }

    public void deleteProject(Long projectId) {
        projectRepository.deleteProject(projectId);
    }

}
