package com.example.zero.group.domain.model;

import com.example.zero.group.domain.model.enums.GroupAuthority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class UpdateGroupPreferenceDto {
    private final Long groupId;
    private final Timestamp deadline;
    private final GroupAuthority authority;
}
