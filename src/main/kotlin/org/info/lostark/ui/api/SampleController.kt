package org.info.lostark.ui.api

import java.time.Instant
import java.time.LocalDateTime
import java.util.*
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SampleController {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("timezone")
    fun time(): ResponseEntity<TimeTime> {
        val tz = Calendar.getInstance().timeZone
        log.info("time zone is ${tz}")
        return ResponseEntity.ok(TimeTime())
    }
}

data class TimeTime(
    val localDateTime: LocalDateTime = LocalDateTime.now(),
    val instant: Instant = Instant.now()
)