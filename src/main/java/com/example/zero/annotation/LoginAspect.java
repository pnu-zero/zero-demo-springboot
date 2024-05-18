package com.example.zero.annotation;

import com.example.zero.user.domain.model.User;
import com.example.zero.utils.SessionUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.JoinPoint;


@Aspect
@Component
public class LoginAspect {

    @Before("@annotation(com.example.zero.annotation.LoginRequired)")
    public void memberLoginCheck(JoinPoint jp) throws Throwable {
        HttpSession session = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
        User user = (User) session.getAttribute("USER");
        if (user == null) {
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "NO_LOGIN") {};
        }
    }
}
