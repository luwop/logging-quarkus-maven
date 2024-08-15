package edu.uchicago.gerber.quark.services

import edu.uchicago.gerber.quark.models.Logging
import edu.uchicago.gerber.quark.repository.MongoLoggingRepository
import io.quarkus.mongodb.panache.kotlin.PanacheQuery
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class LoggingService {
    @Inject
    lateinit var concreteRepository: MongoLoggingRepository

    fun create(log: Logging) {
        concreteRepository._create(log)
    }

    fun create(logs: List<Logging>) {
        concreteRepository._create(logs)
    }

    fun readById(logId: String): Logging {
        return concreteRepository._readById(logId)
    }

    fun readAll(): List<Logging> {
        return concreteRepository._readAll()
    }

    fun deleteById(logId: String): Logging? {
        return concreteRepository._deleteById(logId)
    }

    fun deleteAll(): List<Logging> {
        return concreteRepository._deleteAll()
    }

    fun count(): Long {
        return concreteRepository.count()
    }

    fun findAll(): PanacheQuery<Logging> {
        return concreteRepository._findAll()
    }
}
