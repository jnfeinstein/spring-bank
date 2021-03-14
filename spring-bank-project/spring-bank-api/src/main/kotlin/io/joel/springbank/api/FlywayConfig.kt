package io.joel.springbank.api

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlywayConfig {
    @Bean(initMethod = "migrate")
    fun flyway(
        @Value("\${db.url}") url: String,
        @Value("\${db.user}") user: String,
        @Value("\${db.password}") password: String,
    ): Flyway {
        return Flyway(
            Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource("jdbc:$url", user, password)
        )
    }
}
