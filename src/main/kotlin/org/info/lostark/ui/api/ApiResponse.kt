package org.info.lostark.ui.api

data class ApiResponse<T>(
    val message: String? = null,
    val data: T? = null
) {
    companion object {
        fun <T> success(data: T?): ApiResponse<T> = ApiResponse(data = data)

        fun error(message: String?): ApiResponse<Any> = ApiResponse(message = message)
    }
}
