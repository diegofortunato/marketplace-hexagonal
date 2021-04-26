package com.itau.orange.marketplace.port.output

import com.itau.orange.marketplace.adapter.output.entity.CustomerEntity
import io.micronaut.data.model.Pageable
import java.util.Optional
import java.util.stream.Stream

interface CustomerOutputPort {
    fun findAll(page: Pageable): Stream<CustomerEntity>
    fun findById(id: Long): Optional<CustomerEntity>
    fun save(customer: CustomerEntity): CustomerEntity
    fun update(customer: CustomerEntity): CustomerEntity
    fun delete(customer: CustomerEntity)
}
