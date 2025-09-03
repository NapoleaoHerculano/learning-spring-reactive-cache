package com.napoleao.async.cache.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("agenda")
data class Agenda(
    @Id
    val id: Long,
    val description: String
)