package com.itau.orange.marketplace.adapter.datastore.entity

import com.itau.orange.marketplace.adapter.datastore.entity.AddressEntity
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class CustomerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,

    @Column(name = "name", length = 200)
    var name: String,

    @OneToOne(cascade = [CascadeType.ALL])
    val address: AddressEntity
)
