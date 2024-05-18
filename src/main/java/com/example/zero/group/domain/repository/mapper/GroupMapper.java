package com.example.zero.group.domain.repository.mapper;

import com.example.zero.group.domain.model.GroupDto;
import com.example.zero.group.domain.model.GroupResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroupMapper {
    Long save(GroupDto group);
    void makeRelation(Long userId, Long groupId);
    List<GroupResponseDto> findAllGroups();
    List<GroupResponseDto> findGroupsByUserId(Long userId);
}
