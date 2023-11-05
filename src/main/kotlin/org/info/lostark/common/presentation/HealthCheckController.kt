package org.info.lostark.common.presentation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {
    @GetMapping("/health")
    fun health(): String {
        return "health"
    }
}