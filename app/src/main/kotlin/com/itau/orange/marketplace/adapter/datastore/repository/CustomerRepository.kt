package com.itau.orange.marketplace.adapter.datastore.repository

import com.itau.orange.marketplace.adapter.datastore.entity.CustomerEntity
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository

@Repository
interface CustomerRepository : PageableRepository<CustomerEntity, Long>
