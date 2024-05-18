package com.example.zero.group.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroupAuthority {
    Private("private"),
    Public("public")
    ;

    private String description;
}
