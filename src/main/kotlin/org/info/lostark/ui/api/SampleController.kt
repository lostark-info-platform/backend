package org.info.lostark.ui.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SampleController {
    @GetMapping("/")
    fun get(): ResponseEntity<Any> {
        return ResponseEntity.ok(mapOf("1" to "2"))
    }
}