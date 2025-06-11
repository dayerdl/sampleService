
package com.example.demo

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.Serializable
import io.ktor.serialization.kotlinx.json.*

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT")?.toInt() ?: 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        get("/users") {
            val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
            val size = call.request.queryParameters["size"]?.toIntOrNull() ?: 10

            val allUsers = (1..100).map { User(it, "User$it") }
            val pagedUsers = allUsers.drop((page - 1) * size).take(size)

            call.respond(PaginatedResponse(pagedUsers, page, size, allUsers.size))
        }
    }
}

@Serializable
data class User(val id: Int, val name: String)

@Serializable
data class PaginatedResponse(val users: List<User>, val page: Int, val size: Int, val total: Int)
