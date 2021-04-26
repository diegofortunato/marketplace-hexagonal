package com.itau.orange.marketplace.adapter.output.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class AddressEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,

    @Column(name = "street", length = 200)
    var street: String,

    @Column(name = "street_number")
    var streetNumber: Int,

    @Column(name = "zip_code", length = 15)
    var zipCode: String
)
