package org.info.lostark.ui.api

data class ApiResponse<T>(
    val data: T? = null
) {
    companion object {
        fun <T> success(data: T?): ApiResponse<T> = ApiResponse(data = data)
    }
}
