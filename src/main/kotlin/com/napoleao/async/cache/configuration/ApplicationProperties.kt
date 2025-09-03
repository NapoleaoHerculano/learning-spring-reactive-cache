package com.napoleao.async.cache.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("application")
data class ApplicationProperties(
    val cacheTTL: Long = 30
)