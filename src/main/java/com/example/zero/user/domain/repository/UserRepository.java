package com.example.zero.user.domain.repository;

import com.example.zero.user.domain.model.SignUpRequestDto;
import com.example.zero.user.domain.model.enums.UserState;
import com.example.zero.user.domain.repository.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final UserMapper userMapper;

    public Long createUser(SignUpRequestDto signUpRequestDto) {
        userMapper.createUser(signUpRequestDto);
        return signUpRequestDto.getId();
    }

    public void deleteUser(Long userId) {
        userMapper.deleteUser(userId);
    }

    public int editUser(SignUpRequestDto signUpRequestDto){
        return userMapper.editUser(signUpRequestDto);
    }

    public int activateUser(Long userId) {
        return userMapper.activateUser(userId, UserState.Active);
    }

    public SignUpRequestDto findByEmail(String email) {
        return userMapper.findByEmail(email);
    }
}
