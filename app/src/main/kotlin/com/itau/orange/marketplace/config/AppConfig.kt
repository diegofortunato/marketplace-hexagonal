package com.itau.orange.marketplace.config

import com.itau.orange.marketplace.adapter.out.repository.SQLRepository
import com.itau.orange.marketplace.port.out.CustomerOutputPort
import com.itau.orange.marketplace.usecase.CustomerUseCase
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import javax.inject.Singleton

@Factory
class AppConfig(private val customerOutputPort: CustomerOutputPort) {

    @Singleton
    @Bean
    fun customerSerivce(): CustomerUseCase {
        return CustomerUseCase(customerOutputPort)
    }
}
