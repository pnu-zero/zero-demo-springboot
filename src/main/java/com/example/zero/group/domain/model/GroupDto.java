package com.example.zero.group.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Getter
@RequiredArgsConstructor
public class GroupDto {
    private Long id;

    @NotBlank(message = "그룹 이름은 필수 항목입니다.")
    @Size(max = 10, message = "그룹 이름은 최대 10자입니다.")
    private final String class_name;

    @NotBlank(message = "그룹 번호는 필수 항목입니다.")
    @Size(min = 3, message = "그룹 번호는 최소 3자입니다.")
    private final String class_num;

    @Setter
    @Size(max = 8, message = "비밀번호는 최대 8자입니다.")
    private String password;

    private Timestamp deadline;
}
