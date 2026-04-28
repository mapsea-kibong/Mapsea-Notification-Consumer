package com.mapsea.notificationconsumer.feature.user.repository

import com.mapsea.notificationconsumer.domain.Company
import com.mapsea.notificationconsumer.domain.User
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class UserQueryRepository(
    private val userRepository: UserRepository,
) {
    fun findUserList(userIdList: List<UUID>): List<User> =
        userRepository.findAll {
            select(entity(User::class))
                .from(
                    entity(User::class),
                    fetchJoin(User::company),
                )
                .where(path(User::userId).`in`(userIdList))
        }.filterNotNull()
}
