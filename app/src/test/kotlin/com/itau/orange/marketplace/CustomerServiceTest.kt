package com.itau.orange.marketplace

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.itau.orange.marketplace.adapter.output.entity.AddressEntity
import com.itau.orange.marketplace.adapter.output.entity.CustomerEntity
import com.itau.orange.marketplace.port.output.CustomerOutputPort
import com.itau.orange.marketplace.usecase.CustomerUseCase
import io.micronaut.data.model.Pageable
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import org.junit.jupiter.api.Test
import java.util.Optional
import java.util.stream.Stream

class CustomerServiceTest {

    @Test
    fun `should find all Customers`() {
        val customerOutputPort = mockk<CustomerOutputPort>()
        val customerService = CustomerUseCase(customerOutputPort)

        val request = FindAllRequest.newBuilder().setPage(1).setSize(10).build()
        val responseService = customerList()

        every { customerOutputPort.findAll(Pageable.from(request.page, request.size)) } returns responseService

        val findAllResponse = customerService.findAllCustomer(request)

        assertThat(findAllResponse.customersCount).isEqualTo(2)
        assertThat(findAllResponse.getCustomers(0).id).isEqualTo(1L)
        assertThat(findAllResponse.getCustomers(1).id).isEqualTo(2L)
        assertThat(findAllResponse.getCustomers(0).name).isEqualTo("John")
        assertThat(findAllResponse.getCustomers(1).name).isEqualTo("Michael")
    }

    @Test
    fun `should find Customers by ID`() {
        val customerOutputPort = mockk<CustomerOutputPort>()
        val customerService = CustomerUseCase(customerOutputPort)
        val request = FindCustomerRequest.newBuilder().setId(1L).build()

        val response = saveCustomer(1L, "John", "Street 1", 1, "000001")

        every { customerOutputPort.findById(1L) } returns Optional.of(response)

        val customer = customerService.findCustomerById(request)

        assertThat(customer.id).isEqualTo(1L)
        assertThat(customer.name).isEqualTo("John")
        assertThat(customer.street).isEqualTo("Street 1")
        assertThat(customer.streetNumber).isEqualTo(1)
        assertThat(customer.zipCode).isEqualTo("000001")
    }

    @Test
    fun `should save Customer`() {
        val customerOutputPort = mockk<CustomerOutputPort>()
        val customerService = CustomerUseCase(customerOutputPort)

        val savedCustomerEntity = saveCustomer(1L, "John", "Street 1", 1, "000001")
        val request = saveCustomerRequest("John", "Street 1", 1, "000001")

        every { customerOutputPort.save(ofType(CustomerEntity::class)) } returns savedCustomerEntity

        val customer = customerService.saveCustomer(request)

        assertThat(customer.id).isEqualTo(1L)
        assertThat(customer.name).isEqualTo("John")
        assertThat(customer.street).isEqualTo("Street 1")
        assertThat(customer.streetNumber).isEqualTo(1)
        assertThat(customer.zipCode).isEqualTo("000001")
    }

    @Test
    fun `should update Customer`() {
        val customerOutputPort = mockk<CustomerOutputPort>()
        val customerService = CustomerUseCase(customerOutputPort)

        val response = saveCustomer(1L, "John", "Street 1", 1, "000001")
        val request = updateCustomer(1L, "John", "Street 1", 2, "000001")
        val updatedCustomerEntity = saveCustomer(1L, "John", "Street 1", 2, "000001")

        every { customerOutputPort.findById(1L) } returns Optional.of(response)
        every { customerOutputPort.update(ofType(CustomerEntity::class)) } returns updatedCustomerEntity

        val customer = customerService.updateCustomer(request)

        assertThat(customer.id).isEqualTo(1L)
        assertThat(customer.name).isEqualTo("John")
        assertThat(customer.street).isEqualTo("Street 1")
        assertThat(customer.streetNumber).isEqualTo(2)
        assertThat(customer.zipCode).isEqualTo("000001")
    }

    @Test
    fun `should delete Customer`() {
        val customerOutputPort = mockk<CustomerOutputPort>()
        val customerService = CustomerUseCase(customerOutputPort)

        val request = FindCustomerRequest.newBuilder().setId(1L).build()
        val response = saveCustomer(1L, "John", "Street 1", 1, "000001")

        every { customerOutputPort.findById(1L) } returns Optional.of(response)
        justRun { customerOutputPort.delete(ofType(CustomerEntity::class)) }

        val customer = customerService.deleteCustomer(request)

        assertThat(customer.id).isEqualTo(1L)
        assertThat(customer.name).isEqualTo("John")
        assertThat(customer.street).isEqualTo("Street 1")
        assertThat(customer.streetNumber).isEqualTo(1)
        assertThat(customer.zipCode).isEqualTo("000001")
    }

    private fun saveCustomer(id: Long, name: String, street: String, number: Int, zipCode: String): CustomerEntity {
        val savedAddressEntity = AddressEntity(id, street, number, zipCode)
        return CustomerEntity(id, name, savedAddressEntity)
    }

    private fun updateCustomer(id: Long, name: String, street: String, number: Int, zipCode: String): UpdateCustomerRequest {
        return UpdateCustomerRequest
            .newBuilder()
            .setId(id)
            .setName(name)
            .setStreet(street)
            .setStreetNumber(number)
            .setZipCode(zipCode)
            .build()
    }

    private fun saveCustomerRequest(name: String, street: String, number: Int, zipCode: String): SaveCustomerRequest {
        return SaveCustomerRequest
            .newBuilder()
            .setName(name)
            .setStreet(street)
            .setStreetNumber(number)
            .setZipCode(zipCode)
            .build()
    }

    private fun customerList(): Stream<CustomerEntity> {
        val customerList = arrayListOf<CustomerEntity>()

        customerList.add(saveCustomer(1L, "John", "Street", 1, "000012"))
        customerList.add(saveCustomer(2L, "Michael", "Street", 2, "000012"))

        return customerList.stream()
    }
}
