package com.example.demo.controller

import com.example.demo.dto.PaginatedResponse
import com.example.demo.model.User
import com.example.demo.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun getUsers(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): PaginatedResponse<User> {
        val (data, total) = userService.getUsers(page, size)
        return PaginatedResponse(data, page, size, total)
    }
}