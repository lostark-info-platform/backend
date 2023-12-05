package org.info.lostark.officialschedule.infra.config

import org.info.lostark.common.config.AbstractHttpInterfaceConfig
import org.info.lostark.officialschedule.infra.client.OfficialScheduleClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ScheduleHttpInterfaceConfig : AbstractHttpInterfaceConfig() {
    @Bean
    fun officialScheduleClient(): OfficialScheduleClient {
        return createHttpInterface(OfficialScheduleClient::class.java)
    }

}