package com.example.zero.deployment.service;

import com.example.zero.project.domain.repository.ProjectRepository;
import com.example.zero.user.domain.model.User;
import com.example.zero.user.domain.model.enums.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeployService {
    private final String SESSION_DOMAIN_NAMESPACE = "SESSION_DOMAIN:";
    private final String PROJECT_STATIC_NAMESPACE = "PROJECT_STATIC";
    private final String PROJECT_DYNAMIC_NAMESPACE = "PROJECT_DYNAMIC";

    private final ProjectRepository projectRepository;

    private final RedisTemplate<String, String> redisTemplate;

    public DeployService(ProjectRepository projectRepository, @Qualifier("secondRedisTemplate") RedisTemplate<String, String> redisTemplate) {
        this.projectRepository = projectRepository;
        this.redisTemplate = redisTemplate;
    }

    public String getUserDomain(User user){
        return projectRepository.getUserSubDomain(user.getId(), user.getGroup_id());
    }

    public void pushSessionDomain(String sessionId, String domain, UserRole userRole){
        if(userRole == UserRole.User){
            redisTemplate.opsForValue().set(SESSION_DOMAIN_NAMESPACE + sessionId, domain);
        }else {
            redisTemplate.opsForValue().set(SESSION_DOMAIN_NAMESPACE + sessionId, "*");
        }
    }

    public void pushStaticDomain(String domain){
        redisTemplate.opsForList().rightPush(PROJECT_STATIC_NAMESPACE, domain);
    }

    public void pushDynamicDomain(String domain){
        redisTemplate.opsForList().rightPush(PROJECT_DYNAMIC_NAMESPACE, domain);
    }
}
