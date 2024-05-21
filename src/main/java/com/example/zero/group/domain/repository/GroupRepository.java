package com.example.zero.group.domain.repository;

import com.example.zero.group.domain.model.GroupDto;
import com.example.zero.group.domain.model.GroupResponseDto;
import com.example.zero.group.domain.model.UpdateGroupPreferenceDto;
import com.example.zero.group.domain.repository.mapper.GroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GroupRepository {
    private final GroupMapper groupMapper;

    public Long createGroup(GroupDto groupDto) {
        return groupMapper.save(groupDto);
    }

    public void createGroupUserRelation(Long userId, Long groupId){
        groupMapper.makeRelation(userId, groupId);
    }

    public int updateGroupPreference(UpdateGroupPreferenceDto updateGroupPreferenceDto){
        return groupMapper.updateGroupPreference(updateGroupPreferenceDto);
    }

    public List<GroupResponseDto> findAllGroups() {
        return groupMapper.findAllGroups();
    }

    public List<GroupResponseDto> findGroupsByUserId(Long userId) {
        return groupMapper.findGroupsByUserId(userId);
    }
}
