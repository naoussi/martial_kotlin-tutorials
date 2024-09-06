package com.baeldung.functional_dsl.router_function

import com.baeldung.functional_dsl.UsersRepository;
import com.baeldung.functional_dsl.User;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router

@Import(UsersRepository::class)
@SpringBootApplication
open class ReactiveConfig {

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Bean
    open fun configure(): RouterFunction<ServerResponse> {
        return RouterFunctions.route()
            .GET("/users/{id}") {
                ServerResponse
                    .ok()
                    .body(usersRepository.findUserById(it.pathVariable("id").toLong()))
            }
            .POST("/create") {
                usersRepository.createUsers(it.bodyToMono(User::class.java))
                return@POST ServerResponse
                    .ok()
                    .build()
            }
            .build()
    }
}

fun main(vararg args: String) {
    runApplication<ReactiveConfig>(*args) {}
}