package com.itau.orange.marketplace.exception

data class CustomerNotFoundException(
    val id: Long,
    override val message: String = "Customer not found"
) : Exception(message)

data class InvalidCustomerIdException(
    val id: Long,
    override val message: String = "Customer ID is invalid"
) : Exception(message)

data class InternalErrorException(override val message: String = "Unknown error occured") : Exception()
