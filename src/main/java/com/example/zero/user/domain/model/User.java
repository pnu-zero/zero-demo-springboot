package com.example.zero.user.domain.model;

import com.example.zero.user.domain.model.enums.UserRole;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Builder
public class User implements Serializable {
    private final Long id;
    private final String email;
    private final String student_id;
    private final String name;
    private final UserRole user_role;
    private final Long group_id;
}
