package com.example.zero.configuration;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.time.Duration;

@Configuration
@EnableRedisRepositories
public class RedisForDomainConfig {
    @Value("${spring.data.redis-for-domain.port}")
    private int port;

    @Value("${spring.data.redis-for-domain.host}")
    private String host;

    @Bean("secondLettuceClientConfiguration")
    public LettuceClientConfiguration lettuceClientConfiguration() {

        return LettuceClientConfiguration
                .builder()
                .clientOptions(ClientOptions.builder().autoReconnect(true)
                        .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                        .socketOptions(SocketOptions.builder()
                                .connectTimeout(Duration.ofSeconds(5))
                                .build())
                        .build())
                .commandTimeout(Duration.ofSeconds(30))
                .shutdownTimeout(Duration.ZERO)
                .build();
    }

    @Bean("secondRedisStandaloneConfiguration")
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {

        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
        redisConfiguration.setHostName(host);
        redisConfiguration.setPort(port);

        return redisConfiguration;
    }

    @Bean("secondLettuceConnectionFactory")
    public LettuceConnectionFactory redisConnectionFactory(@Qualifier("secondRedisStandaloneConfiguration") RedisStandaloneConfiguration redisStandaloneConfiguration,@Qualifier("secondLettuceClientConfiguration") LettuceClientConfiguration lettuceClientConfiguration) {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
        lettuceConnectionFactory.setValidateConnection(true);
        return lettuceConnectionFactory;
    }

    @Bean("secondRedisTemplate")
    public RedisTemplate<?, ?> redisTemplate(@Qualifier("secondLettuceConnectionFactory") LettuceConnectionFactory connectionFactory) {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

}
