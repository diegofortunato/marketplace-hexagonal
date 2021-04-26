package com.itau.orange.marketplace.adapter.api.extension

import com.itau.orange.marketplace.SaveCustomerRequest
import com.itau.orange.marketplace.adapter.datastore.entity.AddressEntity
import com.itau.orange.marketplace.adapter.datastore.entity.CustomerEntity

fun SaveCustomerRequest.toCustomerEntity() = CustomerEntity(
    id = null,
    name = this.name,
    address = AddressEntity(null, this.street, this.streetNumber, this.zipCode)
)
