package com.example.zero.user.domain.repository.mapper;

import com.example.zero.user.domain.model.SignUpRequestDto;
import com.example.zero.user.domain.model.enums.UserState;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    Long createUser(SignUpRequestDto user);

    void deleteUser(Long id);

    int editUser(SignUpRequestDto user);

    int activateUser(Long userId, UserState state);

    SignUpRequestDto findByEmail(String email);
}
