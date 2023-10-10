package org.info.lostark.ui.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.slot
import org.info.lostark.fixture.createUser
import org.info.lostark.security.LoginFailedException
import org.info.lostark.security.LoginUser
import org.info.lostark.security.LoginUserResolver
import org.info.lostark.support.TestEnvironment
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.web.servlet.MockHttpServletRequestDsl
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.ContentResultMatchersDsl
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.request.NativeWebRequest

@TestEnvironment
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension::class)
abstract class RestControllerTest {
    @MockkBean
    private lateinit var loginUserResolver: LoginUserResolver

    @Autowired
    lateinit var objectMapper: ObjectMapper

    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp(webApplicationContext: WebApplicationContext, restDocumentation: RestDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .apply<DefaultMockMvcBuilder>(
                MockMvcRestDocumentation
                    .documentationConfiguration(restDocumentation)
            )
            .build()

        loginUserResolver.also { resolver ->
            val methodParameterSlot = slot<MethodParameter>()
            every { resolver.supportsParameter(capture(methodParameterSlot)) } answers {
                val methodParameter = methodParameterSlot.captured
                val hasAnnotation = methodParameter.hasParameterAnnotation(LoginUser::class.java)
                hasAnnotation
            }

            val nativeWebRequestSlot = slot<NativeWebRequest>()
            every { resolver.resolveArgument(any(), any(), capture(nativeWebRequestSlot), any()) } answers {
                val nativeWebRequest = nativeWebRequestSlot.captured
                val hasToken = nativeWebRequest.getHeader(HttpHeaders.AUTHORIZATION)?.startsWith("Bearer", true)
                if (hasToken != true) {
                    throw LoginFailedException()
                }
                createUser()
            }
        }
    }

    fun MockHttpServletRequestDsl.jsonContent(value: Any) {
        content = objectMapper.writeValueAsString(value)
        contentType = MediaType.APPLICATION_JSON
    }

    fun ContentResultMatchersDsl.success(value: Any) {
        json(objectMapper.writeValueAsString(ApiResponse.success(value)), true)
    }
}