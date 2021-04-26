package com.itau.orange.marketplace.adapter.api.controller

import com.itau.orange.marketplace.Customer
import com.itau.orange.marketplace.CustomerServiceGrpcKt
import com.itau.orange.marketplace.FindAllRequest
import com.itau.orange.marketplace.FindAllResponse
import com.itau.orange.marketplace.FindCustomerRequest
import com.itau.orange.marketplace.SaveCustomerRequest
import com.itau.orange.marketplace.UpdateCustomerRequest
import com.itau.orange.marketplace.adapter.api.exception.CustomerNotFoundException
import com.itau.orange.marketplace.adapter.api.exception.InternalErrorException
import com.itau.orange.marketplace.adapter.api.exception.InvalidCustomerIdException
import com.itau.orange.marketplace.core.port.CustomerServicePort
import io.grpc.Status
import io.grpc.StatusException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class CustomerController(private val customerServicePort: CustomerServicePort) :
    CustomerServiceGrpcKt.CustomerServiceCoroutineImplBase() {

    private val log = LoggerFactory.getLogger(javaClass)

    override suspend fun findAllCustomers(request: FindAllRequest) = runCatching {
        customerServicePort.findAllCustomer(request).also {
            log.info("Find All Customers")
        }
    }.onFailure { throw it.toStatusException() }.getOrThrow()

    override fun findAllCustomersStream(request: FindAllRequest): Flow<FindAllResponse> = flow {
        log.info("Find All Customers Stream")

        val customers = customerServicePort.findAllCustomer(request)

        val customerBuilder = FindAllResponse.newBuilder()
        customers.customersList.forEach { it ->
            emit(customerBuilder.addCustomers(it).build())
        }
    }

    override suspend fun findCustomerById(request: FindCustomerRequest) = runCatching {
        customerServicePort.findCustomerById(request).also {
            log.info("Find Customer by ID: ", request.id)
        }
    }.onFailure { throw it.toStatusException() }.getOrThrow()

    override suspend fun saveCustomer(request: SaveCustomerRequest): Customer {
        return customerServicePort.saveCustomer(request).also {
            log.info("Customer saved")
        }
    }

    override fun saveCustomerStream(requests: Flow<SaveCustomerRequest>): Flow<Customer> = flow {
        log.info("Customer Stream save")
        requests.collect { emit(customerServicePort.saveCustomer(it)) }
    }

    override suspend fun updateCustomer(request: UpdateCustomerRequest): Customer {
        log.info("Customer Updated")
        return customerServicePort.updateCustomer(request)
    }

    override fun updateCustomerStream(requests: Flow<UpdateCustomerRequest>): Flow<Customer> = flow {
        log.info("Customer Stream Updated")
        requests.collect { emit(customerServicePort.updateCustomer(it)) }
    }

    override suspend fun deleteCustomer(request: FindCustomerRequest): Customer {
        log.info("Customer Delete")
        return customerServicePort.deleteCustomer(request)
    }

    override fun deleteCustomerStream(requests: Flow<FindCustomerRequest>): Flow<Customer> = flow {
        log.info("Customer Stream Delete")
        requests.collect { emit(customerServicePort.deleteCustomer(it)) }
    }

    private fun Throwable.toStatusException() = when (this) {
        is CustomerNotFoundException -> StatusException(Status.NOT_FOUND.augmentDescription(this.message))
        is InvalidCustomerIdException -> StatusException(Status.INVALID_ARGUMENT.augmentDescription(this.message))
        is InternalErrorException -> StatusException(Status.INTERNAL.augmentDescription(this.message))
        else ->
            StatusException(Status.INTERNAL.augmentDescription("${this.javaClass.simpleName}: ${this.localizedMessage}"))
                .also {
                    log.error("An internal server error has occurred")
                }
    }
}
