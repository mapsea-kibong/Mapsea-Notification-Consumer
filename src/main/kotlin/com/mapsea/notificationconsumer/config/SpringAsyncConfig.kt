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
    fun threadPoolTaskExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 20
        executor.maxPoolSize = 100
        executor.queueCapacity = 10000
        executor.setThreadNamePrefix("notification-task")
        executor.setRejectedExecutionHandler(ThreadPoolExecutor.DiscardOldestPolicy())
        executor.initialize()
        return executor
    }
}
