package com.example.zero.group.controller;

import com.example.zero.annotation.LoginRequired;
import com.example.zero.group.domain.model.GroupDto;
import com.example.zero.group.domain.model.GroupResponseDto;
import com.example.zero.group.service.GroupService;
import com.example.zero.utils.SessionUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<List<GroupResponseDto>> getGroups() {
        List<GroupResponseDto> allGroups = groupService.getAllGroups();
        return ResponseEntity.ok().body(allGroups);
    }

    @GetMapping("/find_by_user")
    @LoginRequired
    public ResponseEntity<List<GroupResponseDto>> getGroupsByUserId(HttpSession session) {
        Long userId = SessionUtils.getLoginUserId(session);
        List<GroupResponseDto> groupsByUserId = groupService.getAllGroupsByUserId(userId);
        return ResponseEntity.ok().body(groupsByUserId);
    }

    @PostMapping
    @LoginRequired
    public ResponseEntity<GroupDto> createGroup(@RequestBody @Valid GroupDto groupDto) {
        GroupDto createdGroup = groupService.createGroup(groupDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    @PutMapping
    @LoginRequired
    public ResponseEntity<GroupDto> editGroup(@RequestBody @Valid GroupDto groupDto) {
        GroupDto createdGroup = groupService.createGroup(groupDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }
}
