package com.itau.orange.marketplace.adapter.output

import com.itau.orange.marketplace.adapter.output.entity.CustomerEntity
import com.itau.orange.marketplace.adapter.output.repository.SQLRepository
import com.itau.orange.marketplace.port.output.CustomerOutputPort
import io.micronaut.data.model.Pageable
import java.util.Optional
import java.util.stream.Stream
import javax.inject.Singleton

@Singleton
class CustomerPersistenceAdapter(private val sqlRepository: SQLRepository) :
    CustomerOutputPort {

    override fun findAll(page: Pageable): Stream<CustomerEntity> = sqlRepository.findAll(page).content.stream()

    override fun findById(id: Long): Optional<CustomerEntity> = sqlRepository.findById(id)

    override fun save(customer: CustomerEntity): CustomerEntity = sqlRepository.save(customer)

    override fun update(customer: CustomerEntity): CustomerEntity = sqlRepository.update(customer)

    override fun delete(customer: CustomerEntity) = sqlRepository.delete(customer)
}
