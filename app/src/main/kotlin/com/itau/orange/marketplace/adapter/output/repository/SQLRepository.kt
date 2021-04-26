package com.itau.orange.marketplace.adapter.output.repository

import com.itau.orange.marketplace.adapter.output.entity.CustomerEntity
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository

@Repository
interface SQLRepository : PageableRepository<CustomerEntity, Long>
