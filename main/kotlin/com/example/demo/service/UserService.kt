package com.example.demo.service

import com.example.demo.model.User
import org.springframework.stereotype.Service

@Service
class UserService {

    private val users = (1..100).map {
        User(
            id = it.toLong(),
            name = "User $it",
            email = "user$it@example.com"
        )
    }

    fun getUsers(page: Int, size: Int): Pair<List<User>, Int> {
        val fromIndex = page * size
        val toIndex = minOf(fromIndex + size, users.size)

        if (fromIndex >= users.size) {
            return Pair(emptyList(), users.size)
        }

        val pageItems = users.subList(fromIndex, toIndex)
        return Pair(pageItems, users.size)
    }
}