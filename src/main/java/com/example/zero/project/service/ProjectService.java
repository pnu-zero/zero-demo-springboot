package com.example.zero.project.service;

import com.example.zero.deployment.service.DeployService;
import com.example.zero.project.domain.model.Project;
import com.example.zero.project.domain.model.ProjectDetail;
import com.example.zero.project.domain.model.ProjectDto;
import com.example.zero.group.domain.model.enums.GroupAuthority;
import com.example.zero.project.domain.model.ProjectWithUser;
import com.example.zero.project.domain.repository.ProjectRepository;
import com.example.zero.project.exception.DuplicateSubDomainException;
import com.example.zero.user.domain.model.User;
import com.example.zero.utils.FileUtils;
import com.example.zero.utils.SessionUtils;
import jakarta.servlet.http.HttpSession;
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
    private final DeployService deployService;

    @Transactional
    public Project createProject(HttpSession session, ProjectDto projectDto, MultipartFile staticFile, MultipartFile dynamicFile) throws IOException {

        // 중복되는 서브 도메인이 있는지 체크
        validateSubdomain(projectDto.getSub_domain());

        // Static File 저장 경로 생성
        String generatedStaticFilePath = FileUtils.generateFilePath(FileUtils.STATIC_FILE_DIR);

        // Static File 이름을 DTO 에 저장
        projectDto.setStatic_file_name(staticFile.getOriginalFilename());

        // Static File 서버 내에 저장
        String staticFileSrc = FileUtils.uploadStaticFile(generatedStaticFilePath, staticFile, projectDto.getSub_domain());

        // Static Domain Redis push
        deployService.pushStaticDomain(projectDto.getSub_domain());

        String dynamicFileSrc = "";

        if (!(dynamicFile == null || dynamicFile.isEmpty() || dynamicFile.getSize() == 0)) {
            String generatedDynamicFilePath = FileUtils.generateFilePath(FileUtils.DYNAMIC_FILE_DIR);
            projectDto.setDynamic_file_name(dynamicFile.getOriginalFilename());
            dynamicFileSrc = FileUtils.uploadStaticFile(generatedDynamicFilePath, dynamicFile, projectDto.getSub_domain());

            // Dynamic Domain Redis push
            deployService.pushDynamicDomain(projectDto.getSub_domain());
        }

        projectDto.setStatic_file_src(staticFileSrc);
        projectDto.setDynamic_file_src(dynamicFileSrc);

        // SessionDomain 업데이트
        User user = SessionUtils.getLoginUser(session);
        deployService.pushSessionDomain(SessionUtils.getUserSessionKey(session), projectDto.getSub_domain(), user.getUser_role());

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
