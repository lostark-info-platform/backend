package org.info.lostark.user.presentation

import org.info.lostark.common.presentation.ApiResponse
import org.info.lostark.common.security.LoginUser
import org.info.lostark.user.command.domain.User
import org.info.lostark.user.query.dto.UserQueryResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/users")
@RestController
class UserInfoRestController {
    @GetMapping("/me")
    fun me(@LoginUser user: User): ApiResponse<UserQueryResponse> {
        return ApiResponse.success(UserQueryResponse(user))
    }
}