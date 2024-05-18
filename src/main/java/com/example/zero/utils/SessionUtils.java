package com.example.zero.utils;

import com.example.zero.user.domain.model.User;
import com.example.zero.user.domain.model.enums.UserRole;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SessionUtils {

    private static final String LOGIN_USER = "USER";

    public static Long getLoginUserId(HttpSession session) {
        User loginedUser = (User) session.getAttribute(LOGIN_USER);
        log.info("loginedUser: {}", loginedUser);
        return loginedUser.getId();
    }

    public static Boolean checkAdmin(HttpSession session) {
        User LoginedUser = (User) session.getAttribute(LOGIN_USER);
        return LoginedUser.getUser_role() == UserRole.Admin;
    }

}
