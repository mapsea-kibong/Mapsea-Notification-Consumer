package com.mapsea.notificationconsumer.redis

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
class RedisConfig {

    @Value("\${spring.data.redis.password}")
    private lateinit var redisPassword: String

    @Value("\${spring.data.redis.host}")
    private lateinit var redisHost: String

    @Value("\${spring.data.redis.port}")
    private var redisPort: Int = 6379

    @Bean
    @Suppress("UNCHECKED_CAST")
    fun redisConnectionFactory(): RedisConnectionFactory {
        val poolConfig = (GenericObjectPoolConfig<Any>().apply {
            maxTotal = 1000
            maxIdle = 1000
            setMaxWait(Duration.ofMinutes(1L))
        }) as GenericObjectPoolConfig<io.lettuce.core.api.StatefulConnection<*, *>>
        val config = RedisStandaloneConfiguration(redisHost, redisPort).apply {
            setPassword(redisPassword)
        }
        val clientConfig = LettucePoolingClientConfiguration.builder()
            .clientName("clientName")
            .poolConfig(poolConfig)
            .commandTimeout(Duration.ofSeconds(100L))
            .shutdownTimeout(Duration.ofMillis(100L))
            .build()
        return LettuceConnectionFactory(config, clientConfig)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> = RedisTemplate<String, Any>().apply {
        keySerializer = StringRedisSerializer()
        valueSerializer = GenericToStringSerializer(Any::class.java)
        hashKeySerializer = StringRedisSerializer()
        hashValueSerializer = GenericToStringSerializer(Any::class.java)
        connectionFactory = redisConnectionFactory()
    }
}
