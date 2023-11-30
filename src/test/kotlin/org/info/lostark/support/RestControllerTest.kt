package org.info.lostark.support

import com.fasterxml.jackson.databind.ObjectMapper
import org.info.lostark.common.presentation.ApiResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
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

@TestEnvironment
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension::class)
abstract class RestControllerTest {
    @Autowired
    lateinit var objectMapper: ObjectMapper

    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp(webApplicationContext: WebApplicationContext, restDocumentation: RestDocumentationContextProvider) {
        mockMvc =
            MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
                .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
    }

    fun MockHttpServletRequestDsl.jsonContent(value: Any) {
        content = objectMapper.writeValueAsString(value)
        contentType = MediaType.APPLICATION_JSON
    }

    fun ContentResultMatchersDsl.success(value: Any) {
        json(objectMapper.writeValueAsString(ApiResponse.success(value)), true)
    }

    fun ContentResultMatchersDsl.error(message: String) {
        json(objectMapper.writeValueAsString(ApiResponse.error(message)), true)
    }
}