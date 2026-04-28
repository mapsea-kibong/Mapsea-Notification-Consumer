package com.mapsea.notificationconsumer.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.netty.resources.ConnectionProvider
import java.time.Duration

@Configuration
class WebClientConfig {

    @Bean("webClientProvider")
    fun webClientProvider(): ConnectionProvider =
        ConnectionProvider.builder("custom-provider")
            .maxConnections(100)
            .maxIdleTime(Duration.ofSeconds(58))
            .maxLifeTime(Duration.ofSeconds(58))
            .pendingAcquireTimeout(Duration.ofMillis(5000))
            .pendingAcquireMaxCount(-1)
            .evictInBackground(Duration.ofSeconds(30))
            .lifo()
            .metrics(true)
            .build()
}
