package com.napoleao.async.cache.controller

import com.napoleao.async.cache.service.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(
    val service: Service
) {

    @GetMapping("/agenda/{id}")
    suspend fun findAgendaById(@PathVariable id: Long) =
        service.findAgendaById(id)

}