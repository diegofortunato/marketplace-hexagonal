package com.itau.orange.marketplace.port.`in`

import com.itau.orange.marketplace.Customer
import com.itau.orange.marketplace.FindAllRequest
import com.itau.orange.marketplace.FindAllResponse
import com.itau.orange.marketplace.FindCustomerRequest
import com.itau.orange.marketplace.SaveCustomerRequest
import com.itau.orange.marketplace.UpdateCustomerRequest

interface CustomerInputPort {

    fun findAllCustomer(request: FindAllRequest): FindAllResponse
    fun findCustomerById(request: FindCustomerRequest): Customer
    fun saveCustomer(request: SaveCustomerRequest): Customer
    fun updateCustomer(request: UpdateCustomerRequest): Customer
    fun deleteCustomer(request: FindCustomerRequest): Customer
}
