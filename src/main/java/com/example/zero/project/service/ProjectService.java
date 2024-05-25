package com.example.zero.project.service;

import com.example.zero.project.domain.model.Project;
import com.example.zero.project.domain.model.ProjectDto;
import com.example.zero.group.domain.model.enums.GroupAuthority;
import com.example.zero.project.domain.model.ProjectWithUser;
import com.example.zero.project.domain.repository.ProjectRepository;
import com.example.zero.project.exception.DuplicateSubDomainException;
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

        validateSubdomain(projectDto.getSub_domain());

        String generatedStaticFilePath = FileUtils.generateFilePath(FileUtils.STATIC_FILE_DIR);
        String staticFileSrc = FileUtils.uploadStaticFile(generatedStaticFilePath, staticFile, projectDto.getSub_domain());
        String dynamicFileSrc = "";

        if (!dynamicFile.isEmpty()) {
            String generatedDynamicFilePath = FileUtils.generateFilePath(FileUtils.DYNAMIC_FILE_DIR);
            dynamicFileSrc = FileUtils.uploadStaticFile(generatedDynamicFilePath, dynamicFile, projectDto.getSub_domain());
        }

        projectDto.setStatic_file_src(staticFileSrc);
        projectDto.setDynamic_file_src(dynamicFileSrc);

        ProjectDto responseProjectDto = projectRepository.createProject(projectDto);

        return Project.from(responseProjectDto);
    }

    public void validateSubdomain(String subdomain) {
        if (projectRepository.isDuplicateSubdomainExist(subdomain) > 0){
            throw new DuplicateSubDomainException("중복되는 도메인이 존재합니다");
        }
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
