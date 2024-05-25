package com.example.zero.user.service;

import com.example.zero.group.service.GroupService;
import com.example.zero.mail.service.MailService;
import com.example.zero.user.domain.model.User;
import com.example.zero.user.domain.model.SignUpRequestDto;
import com.example.zero.user.domain.model.enums.UserRole;
import com.example.zero.user.domain.model.enums.UserState;
import com.example.zero.user.domain.repository.UserRepository;
import com.example.zero.user.domain.repository.mapper.UserMapper;
import com.example.zero.user.exception.ActivationFailedException;
import com.example.zero.user.exception.InvalidRegisterException;
import com.example.zero.user.exception.LoginException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Qualifier("defaultRedisTemplate")
    private final RedisTemplate<String, String> redisTemplate;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final GroupService groupService;

    private final String RedisAuthenticationPrefix = "AuthenticationCode:";

    private void cacheCodeToRedis(Long userId, String uuid) {
        redisTemplate.opsForValue().set(RedisAuthenticationPrefix + userId.toString(), uuid);
    }

    @Transactional
    public Long createUser(SignUpRequestDto signUpRequestDto, Long groupId) throws Exception {

        Boolean hasKey = redisTemplate.hasKey(RedisAuthenticationPrefix.concat(signUpRequestDto.getEmail()));

        // 이미 회원가입 신청이 완료된 유저 이메일에 대한 예외처리
        if (Boolean.TRUE.equals(hasKey)) {
            throw new InvalidRegisterException("이미 회원가입 요청이 된 이메일입니다.");
        }

        // 회원 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        signUpRequestDto.setPassword(encodedPassword);

        // 유저(Disabled 상태) 생성
        Long createdUserId = userRepository.createUser(signUpRequestDto);

        // 유저 verify 코드 생성
        String verifyCode = UUID.randomUUID().toString();

        // UUID(Value) 생성하여, Redis에 ttl(하루)로 생성
        cacheCodeToRedis(createdUserId, verifyCode);

        // 사용자 이메일로 메일 발송
        mailService.sendAuthMail(signUpRequestDto.getEmail(), verifyCode, groupId, createdUserId);

        return createdUserId;
    }

    @Transactional
    public void activateUser(String authCode, Long userId, Long groupId) {
        Boolean hasKey = redisTemplate.hasKey(RedisAuthenticationPrefix.concat(userId.toString()));
        if (Boolean.FALSE.equals(hasKey)) {
            throw new ActivationFailedException("만료된 인증 코드입니다.");
        }

        Objects.requireNonNull(redisTemplate.opsForValue().get(RedisAuthenticationPrefix.concat(userId.toString()))).equals(authCode);

        redisTemplate.delete(RedisAuthenticationPrefix + userId);
        userRepository.activateUser(userId);

        if (groupId != null ){
            groupService.mapGroupToUser(userId, groupId);
        }
    }

    public User authenticateUser(String userEmail, String password) {
        SignUpRequestDto signUpRequestDto = userRepository.findByEmail(userEmail);

        log.info(String.valueOf(signUpRequestDto.getGroup_id()));

        if (signUpRequestDto.getUser_state().equals(UserState.Disabled)){
            throw new LoginException("계정이 비활성화 상태입니다.");
        }

        if (passwordEncoder.matches(password, signUpRequestDto.getPassword())) {
            return User.builder()
                    .id(signUpRequestDto.getId())
                    .email(signUpRequestDto.getEmail())
                    .student_id(signUpRequestDto.getStudent_id())
                    .name(signUpRequestDto.getName())
                    .user_role(signUpRequestDto.getUser_role())
                    .group_id(signUpRequestDto.getGroup_id())
                    .build();
        }

        throw new LoginException("패스워드가 일치하지 않습니다.");
    }
}
