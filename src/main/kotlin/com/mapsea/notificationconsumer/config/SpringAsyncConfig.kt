package com.mapsea.notificationconsumer.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor

@Configuration
@EnableAsync
class SpringAsyncConfig {

    @Bean(name = ["threadPoolTaskExecutor"])
    fun threadPoolTaskExecutor(): Executor = ThreadPoolTaskExecutor().apply {
        corePoolSize = 20
        maxPoolSize = 100
        queueCapacity = 10000
        threadNamePrefix = "notification-task"
        setRejectedExecutionHandler(ThreadPoolExecutor.DiscardOldestPolicy())
        initialize()
    }
}
