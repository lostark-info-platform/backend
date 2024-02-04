package org.info.lostark.common.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun convertStringToDateTime(dateTimeString: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    return LocalDateTime.parse(dateTimeString, formatter)
}
