package com.example.zero.group.service;

import com.example.zero.group.domain.model.GroupDto;
import com.example.zero.group.domain.model.GroupResponseDto;
import com.example.zero.group.domain.model.UpdateGroupPreferenceDto;
import com.example.zero.group.domain.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;

    public List<GroupResponseDto> getAllGroups() {
        return groupRepository.findAllGroups();
    }

    public List<GroupResponseDto> getAllGroupsByUserId(Long userId) {
        return groupRepository.findGroupsByUserId(userId);
    }

    public int updateGroupPreference(UpdateGroupPreferenceDto updateGroupPreferenceDto){
        log.info(updateGroupPreferenceDto.getAuthority().getDescription());
        return groupRepository.updateGroupPreference(updateGroupPreferenceDto);
    }

    @Transactional
    public GroupDto createGroup(GroupDto groupDto) {
        if(!groupDto.getPassword().isEmpty()){
            String encodedPassword = passwordEncoder.encode(groupDto.getPassword());
            groupDto.setPassword(encodedPassword);
        }

        groupRepository.createGroup(groupDto);
        return groupDto;
    }

    @Transactional
    public GroupDto updateGroup(GroupDto groupDto) {
        return null;
    }

    @Transactional
    public void mapGroupToUser(Long userId, Long groupId) {
        groupRepository.createGroupUserRelation(userId, groupId);
    }
}
