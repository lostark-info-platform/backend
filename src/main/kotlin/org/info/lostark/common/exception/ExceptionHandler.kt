package org.info.lostark.common.exception

import org.info.lostark.common.presentation.ApiResponse
import org.info.lostark.common.security.LoginFailedException
import org.info.lostark.user.command.domain.UnidentifiedUserException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {
    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.error("message", ex)
        val message = ex.message.orEmpty()
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(message))
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.error("message", ex)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(ex.messages()))
    }

    @ExceptionHandler(IllegalArgumentException::class, IllegalStateException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<ApiResponse<Any>> {
        logger.error("message", ex)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(ex.message))
    }

    @ExceptionHandler(LoginFailedException::class)
    fun handleLoginFailedException(ex: LoginFailedException): ResponseEntity<ApiResponse<Any>> {
        logger.error("message", ex)
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ApiResponse.error(ex.message))
    }

    @ExceptionHandler(UnidentifiedUserException::class)
    fun handleUnidentifiedUserException(ex: UnidentifiedUserException): ResponseEntity<ApiResponse<Any>> {
        logger.error("message", ex)
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ApiResponse.error(ex.message))
    }

    private fun MethodArgumentNotValidException.messages() =
        bindingResult.fieldErrors.joinToString(", ") { "${it.field}: ${it.defaultMessage.orEmpty()}" }
}