package org.info.lostark.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import java.io.BufferedReader
import java.io.InputStreamReader
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer

@Profile("test")
@Configuration
class EmbeddedRedisConfig(
    private val properties: RedisConfigProperties
) {
    lateinit var redisServer: RedisServer

    @PostConstruct
    fun redisServer() {
        val port = if (isRedisRunning()) findAvailablePort() else properties.port
        redisServer = RedisServer(port)
        redisServer.start()
    }

    @PreDestroy
    fun stopRedis() {
        redisServer.stop()
    }

    private fun isRedisRunning(): Boolean {
        return isRunning(executeGrepProcessCommand(properties.port))
    }

    private fun findAvailablePort(): Int {
        for (port in 10000..65535) {
            val process = executeGrepProcessCommand(port)
            if (!isRunning(process)) {
                return port
            }
        }
        throw IllegalArgumentException("Not Found Available port: 10000~65535")
    }

    private fun executeGrepProcessCommand(port: Int): Process {
        val command = "netstat -nat | grep LISTEN | grep $port"
        val shell = arrayOf("/bin/sh", "-c", command)
        return Runtime.getRuntime().exec(shell)
    }

    private fun isRunning(process: Process): Boolean {
        var line: String?
        val pidInfo = StringBuilder()
        try {
            BufferedReader(InputStreamReader(process.inputStream)).use { input ->
                while (input.readLine().also {
                        line = it
                    } != null) {
                    pidInfo.append(line)
                }
            }
        } catch (e: Exception) {
            throw e
        }
        return pidInfo.toString().isNotEmpty()
    }
}