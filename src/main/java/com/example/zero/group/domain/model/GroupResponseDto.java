package com.example.zero.group.domain.model;

import lombok.*;

import java.util.Date;

@Builder
@RequiredArgsConstructor
@Getter
public class GroupResponseDto {
    private final Long id;
    private final String class_name;
    private final String class_num;
    private final String password;
    private final Date deadline;
}
