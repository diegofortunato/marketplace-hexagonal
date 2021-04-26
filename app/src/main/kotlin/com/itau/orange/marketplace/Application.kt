package com.itau.orange.marketplace

import io.micronaut.runtime.Micronaut.build
fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("com.itau.orange.marketplace")
        .start()
}
