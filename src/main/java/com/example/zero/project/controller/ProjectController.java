package com.example.zero.project.controller;

import com.example.zero.annotation.AdminRoleRequired;
import com.example.zero.annotation.LoginRequired;
import com.example.zero.project.domain.model.Project;
import com.example.zero.project.domain.model.ProjectDto;
import com.example.zero.group.domain.model.enums.GroupAuthority;
import com.example.zero.project.service.ProjectService;
import com.example.zero.user.domain.model.User;
import com.example.zero.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    @LoginRequired
    public ResponseEntity<Project> createProject(HttpSession session, @RequestPart(name = "projectDto") ProjectDto projectDto, @RequestPart(name = "staticFile", required = true) MultipartFile staticFile, @RequestPart(name = "dynamicFile", required = false) MultipartFile dynamicFile) throws IOException {
        Long userId = SessionUtils.getLoginUserId(session);
        Long groupId =  SessionUtils.getLoginUserGroupId(session);
        projectDto.setUser_id(userId);
        projectDto.setGroup_id(groupId);
        Project createdProject = projectService.createProject(projectDto, staticFile, dynamicFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }

    @GetMapping("/find_by_group")
    @LoginRequired
    public ResponseEntity<?> getAllProjectByGroup(@RequestParam("group_id") Long groupId){
        List<ProjectDto> projectDtoList =  projectService.getProjectListByGroupId(groupId);
        return ResponseEntity.ok().body(projectDtoList);
    }

    @GetMapping("/find_by_user")
    @LoginRequired
    public ResponseEntity<?> getProjectByUser(HttpSession session){
        Long userId = SessionUtils.getLoginUserId(session);
        List<ProjectDto> projectDtoList =  projectService.getProjectListByUserId(userId);
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
