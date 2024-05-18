package com.example.zero.user.controller;

import com.example.zero.annotation.LoginRequired;
import com.example.zero.user.domain.model.LoginRequestDto;
import com.example.zero.user.domain.model.User;
import com.example.zero.user.domain.model.SignUpRequestDto;
import com.example.zero.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/api/user/signup")
    public ResponseEntity<Long> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto, @RequestParam(value = "group_id", required = false) Long groupId) throws Exception {
        Long id = userService.createUser(signUpRequestDto, groupId);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("/api/user/activate")
    public ResponseEntity<?> activateUser(@RequestParam("auth_code") String authCode, @RequestParam(value = "user_id", required = true) Long userId, @RequestParam(value = "group_id", required = false) Long groupId) {
        userService.activateUser(authCode, userId, groupId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("계정이 활성화되었습니다.");
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest, HttpServletRequest request) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        User user = userService.authenticateUser(email, password);

        log.info("ASDFASDFASd!!!1");
        log.info(String.valueOf(user));

        if(user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("USER", user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials.");
        }
    }

    @PostMapping("api/user/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/api/user")
    @LoginRequired
    public ResponseEntity<User> getUser(HttpSession session) {
        User user = (User) session.getAttribute("USER");
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/api/user/edit")
    @LoginRequired
    public ResponseEntity<User> editUser() {
        return ResponseEntity.ok().body(null);
    }
}
