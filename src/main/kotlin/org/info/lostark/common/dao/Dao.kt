package org.info.lostark.common.dao

import org.springframework.stereotype.Component
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS


@Target(CLASS)
@Retention(RUNTIME)
@Component
annotation class Dao
