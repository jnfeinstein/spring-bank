package io.joel.springbank.api

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(DataSourceAutoConfiguration::class)
class BankApplication

fun main(args: Array<String>) {
    runApplication<BankApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
