package com.example.zero.project.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectRole {
    Private("private"),
    Public("public")
    ;

    private String description;
}
