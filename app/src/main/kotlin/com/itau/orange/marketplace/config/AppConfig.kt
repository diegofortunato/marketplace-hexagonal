package com.itau.orange.marketplace.config

import com.itau.orange.marketplace.adapter.datastore.repository.CustomerRepository
import com.itau.orange.marketplace.core.service.CustomerService
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import javax.inject.Singleton

@Factory
class AppConfig(private val customerRepository: CustomerRepository) {

    @Singleton
    @Bean
    fun customerSerivce(): CustomerService {
        return CustomerService(customerRepository)
    }
}
