package org.info.lostark.common.presentation

import org.info.lostark.common.application.StaticConfigService
import org.info.lostark.common.presentation.dto.ClientBaseUrlRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/config")
class StaticConfigController(
    private val staticConfigService: StaticConfigService
) {
    @PostMapping("/clientBaseUrl")
    fun changeClientBaseUrl(@RequestBody request: ClientBaseUrlRequest): ApiResponse<Any> {
        staticConfigService.changeClientBaseUrl(request.url)
        return ApiResponse.success(null)
    }
}