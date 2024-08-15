package edu.uchicago.gerber.quark.repository

import edu.uchicago.gerber.quark.models.Logging
import edu.uchicago.gerber.quark.models.Faked
import io.quarkus.mongodb.panache.kotlin.PanacheMongoRepository
import io.quarkus.mongodb.panache.kotlin.PanacheQuery
import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import jakarta.ws.rs.ClientErrorException
import jakarta.ws.rs.NotFoundException
import jakarta.ws.rs.core.Response
import org.bson.types.ObjectId

@ApplicationScoped
class MongoLoggingRepository : PanacheMongoRepository<Logging> {

    fun onStart(@Observes ev: StartupEvent?) {
        if (count() == 0L) {
            val list = mutableListOf<Logging>()
            repeat(1000) { list.add(Faked.genRawEntity()) }
            persist(list)
            persist(Faked.genTestLogging(Faked.FAKE_ID))
        }
    }

    fun _create(log: Logging): Logging {
        val exists = find("sessionEmail = ?1 and event = ?2 and timeStamp = ?3", log.sessionEmail, log.event, log.timeStamp).count() > 0
        if (exists) {
            throw ClientErrorException("A similar log already exists.", Response.Status.CONFLICT)
        }
        this.persist(log)
        return log
    }

    fun _create(logs: List<Logging>): List<Logging> {
        this.persist(logs)
        return listAll()
    }

    fun _readById(id: String): Logging {
        val logId = ObjectId(id)
        val existingLog = this.findById(logId)
        return existingLog ?: throw NotFoundException()
    }

    fun _readAll(): List<Logging> {
        if (this.count() == 0L) {
            throw NotFoundException()
        }
        return this.listAll()
    }

    fun _findBySessionEmail(sessionEmail: String): List<Logging> {
        return this.find("sessionEmail", sessionEmail).list()
    }

    fun _deleteById(id: String): Logging {
        val logId = ObjectId(id)
        val log = _readById(id)
        this.deleteById(logId)
        return log
    }

    fun _deleteAll(): List<Logging> {
        this.deleteAll()
        return emptyList()
    }

    fun _count(): Long {
        return this.count()
    }

    fun _findAll(): PanacheQuery<Logging> {
        return this.findAll()
    }
}