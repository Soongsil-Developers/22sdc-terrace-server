package com.dev_camp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@EnableAsync
@Configuration
class AsyncConfig {
    @Bean("reservationTaskExecutor")
    fun asyncTaskExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 12
        executor.maxPoolSize = 20
        executor.setQueueCapacity(0)
        executor.threadNamePrefix = "reservation-executor-"
        executor.initialize()
        return executor
    }
}