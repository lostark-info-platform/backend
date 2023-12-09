package org.info.lostark.common.application

import org.info.lostark.common.domain.StaticConfig
import org.info.lostark.common.domain.StaticConfigRepository
import org.info.lostark.common.domain.get
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StaticConfigService(
    private val staticConfigRepository: StaticConfigRepository
) {
    fun changeClientBaseUrl(url: String) {
        val staticConfig = staticConfigRepository.get()
        staticConfig?.changeClientBaseUrl(url) ?: staticConfigRepository.save(StaticConfig(url))
    }

    fun get(): StaticConfig {
        val staticConfig = staticConfigRepository.get()
        if (staticConfig != null) {
            return staticConfig
        }
        return staticConfigRepository.save(StaticConfig("http://localhost:3000"))
    }
}