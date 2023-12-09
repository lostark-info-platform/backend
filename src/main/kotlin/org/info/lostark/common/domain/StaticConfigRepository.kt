package org.info.lostark.common.domain

import org.springframework.data.jpa.repository.JpaRepository

fun StaticConfigRepository.get(): StaticConfig? = findAll().lastOrNull()

interface StaticConfigRepository : JpaRepository<StaticConfig, Long>