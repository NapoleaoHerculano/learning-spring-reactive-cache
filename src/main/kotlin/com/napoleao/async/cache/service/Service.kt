package com.napoleao.async.cache.service

import com.napoleao.async.cache.configuration.ApplicationProperties
import com.napoleao.async.cache.domain.Agenda
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class Service(
    private val databaseClient: DatabaseClient,
    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, String>,
    private val properties: ApplicationProperties
) {

    suspend fun findAgendaById(id: Long): Agenda {
        val key = "$REDIS_KEY_NAME:$id"

        reactiveRedisTemplate
            .opsForValue()
            .get(key)
            .awaitFirstOrNull()?.let {
                logger.info("Cache found -> KEY=[$key] | VALUE=[$it]")
                return Agenda(id = id, description = it)
            }

        val agenda = databaseClient
            .sql("SELECT * FROM AGENDA WHERE id = :id")
            .bind("id", id)
            .map { row, _ ->
                Agenda(
                    id = row.get("id")!!.toString().toLong(),
                    description = row.get("description", String::class.java)!!
                )
            }
            .first()
            .switchIfEmpty(
                Mono.error(NoSuchElementException())
            )
            .awaitSingle()

        reactiveRedisTemplate
            .opsForValue()
            .set(
                key,
                agenda.description,
                Duration.ofDays(properties.cacheTTL)
            )
            .awaitSingle()
        return agenda
    }

    companion object {
        @JvmStatic
        private val logger: Logger = LoggerFactory.getLogger(Service::class.java)
        private const val REDIS_KEY_NAME = "agenda"
    }

}