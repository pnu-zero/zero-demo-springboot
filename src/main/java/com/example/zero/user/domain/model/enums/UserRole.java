package com.example.zero.user.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    User("일반 사용자"),
    Admin("관리자")
    ;

    private String description;
}
