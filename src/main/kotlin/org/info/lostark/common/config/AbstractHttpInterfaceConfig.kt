package org.info.lostark.common.config

import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

abstract class AbstractHttpInterfaceConfig {
    protected fun <T> createHttpInterface(clazz: Class<T>): T {
        val webClient = WebClient.create()
        val build = HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(webClient)).build()
        return build.createClient(clazz)
    }
}