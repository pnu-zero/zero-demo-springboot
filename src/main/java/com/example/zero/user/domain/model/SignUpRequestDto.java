package com.example.zero.user.domain.model;


import com.example.zero.user.domain.model.enums.UserRole;
import com.example.zero.user.domain.model.enums.UserState;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class SignUpRequestDto {
    private Long id;

    @NotBlank(message = "이메일 입력은 필수입니다.")
    @Email
    private final String email;

    @NotBlank(message = "학번 입력은 필수입니다.")
    private final String student_id;

    @NotBlank(message = "이름 입력은 필수입니다.")
    private final String name;

    @Setter
    @Size(min = 8, max = 20, message = "비밀번호는 최소 8자 이상, 최대 20자 이하여야 합니다.")
    private String password;

    private final UserState user_state = UserState.Disabled;
    private final UserRole user_role = UserRole.User;

    private Long group_id;
}
