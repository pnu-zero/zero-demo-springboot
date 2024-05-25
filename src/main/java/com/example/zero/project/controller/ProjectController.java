package com.example.zero.project.controller;

import com.example.zero.annotation.AdminRoleRequired;
import com.example.zero.annotation.LoginRequired;
import com.example.zero.project.domain.model.Project;
import com.example.zero.project.domain.model.ProjectDetail;
import com.example.zero.project.domain.model.ProjectDto;
import com.example.zero.group.domain.model.enums.GroupAuthority;
import com.example.zero.project.exception.InvalidGroupIdRequestException;
import com.example.zero.project.exception.NoSearchedContentException;
import com.example.zero.project.service.ProjectService;
import com.example.zero.project.domain.model.ProjectWithUser;
import com.example.zero.utils.SessionUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
@Slf4j
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    @LoginRequired
    public ResponseEntity<Project> createProject(HttpSession session, @RequestPart(name = "projectDto") ProjectDto projectDto, @RequestPart(name = "staticFile", required = true) MultipartFile staticFile, @RequestPart(name = "dynamicFile", required = false) MultipartFile dynamicFile) throws IOException {
        Long userId = SessionUtils.getLoginUserId(session);
        Long groupId =  SessionUtils.getLoginUserGroupId(session);
        projectDto.setUser_id(userId);
        projectDto.setGroup_id(groupId);
        Project createdProject = projectService.createProject(session, projectDto, staticFile, dynamicFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }

    @GetMapping("/search")
    @LoginRequired
    public ResponseEntity<List<ProjectWithUser>> searchProjects(@RequestParam("query") String query){
        List<ProjectWithUser> result = projectService.searchProjectsByQuery(query);
        if(result.isEmpty()){
            throw new NoSearchedContentException("검색 결과가 존재하지 않습니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/validate_domain")
    @LoginRequired
    public ResponseEntity<Boolean> validateDomain(@RequestParam("domain") String domain) {
        projectService.validateSubdomain(domain);
        return ResponseEntity.ok(true);
    }

    @GetMapping
    @LoginRequired
    public ResponseEntity<ProjectDetail> getProjectDetail(@RequestParam("id") Long id){
        ProjectDetail projectDetail = projectService.getProjectDetail(id);
        return ResponseEntity.status(HttpStatus.OK).body(projectDetail);
    }

    @GetMapping("/find_by_group")
    @LoginRequired
    public ResponseEntity<?> getAllProjectByGroup(HttpSession session, @RequestParam("group_id") Long groupId){
        Long loginUserGroupId = SessionUtils.getLoginUserGroupId(session);
        if (!Objects.equals(loginUserGroupId, groupId)){
            throw new InvalidGroupIdRequestException("본인이 속한 그룹 외 정보는 열람이 불가능합니다.");
        }
        List<ProjectWithUser> projectDtoList =  projectService.getProjectListByGroupId(groupId);
        return ResponseEntity.ok().body(projectDtoList);
    }

    @GetMapping("/find_by_user")
    @LoginRequired
    public ResponseEntity<?> getProjectByUser(HttpSession session){
        Long userId = SessionUtils.getLoginUserId(session);
        List<ProjectWithUser> projectDtoList =  projectService.getProjectListByUserId(userId);
        return ResponseEntity.ok().body(projectDtoList);
    }

    @DeleteMapping
    @LoginRequired
    public ResponseEntity<Void> deleteProject(@RequestParam("project_id") Long projectId){
        projectService.deleteProject(projectId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/role")
    @LoginRequired
    @AdminRoleRequired
    public ResponseEntity<Boolean> updateRole(@RequestParam("project_id") Long projectId, @RequestParam("role") GroupAuthority role){
        int result = projectService.updateProjectRole(projectId, role);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
