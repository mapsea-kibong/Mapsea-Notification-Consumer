package com.mapsea.notificationconsumer.cache

import com.github.benmanes.caffeine.cache.Caffeine
import com.mapsea.notificationconsumer.CacheType
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
class CacheConfig {

    @Bean
    fun cacheManager(): CacheManager {
        val caches = CacheType.entries.map { cacheType ->
            CaffeineCache(
                cacheType.cacheName,
                Caffeine.newBuilder()
                    .recordStats()
                    .expireAfterWrite(cacheType.expiredAfterWrite.toLong(), TimeUnit.HOURS)
                    .maximumSize(cacheType.maximumSize.toLong())
                    .build(),
            )
        }
        return SimpleCacheManager().apply { setCaches(caches) }
    }
}
