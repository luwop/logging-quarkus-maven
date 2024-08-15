package edu.uchicago.gerber.quark.resources

import edu.uchicago.gerber.quark.models.Logging
import edu.uchicago.gerber.quark.services.LoggingService
import io.quarkus.mongodb.panache.kotlin.PanacheQuery
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.inject.Inject
import kotlin.math.ceil

@Path("/logging")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class LoggingResource {

    @Inject
    lateinit var loggingService: LoggingService

    val TOTAL_PER_PAGE = 20

    @POST
    fun create(log: Logging): Logging {
        loggingService.create(log)
        return log
    }

    @GET
    fun readAll(): List<Logging> {
        return loggingService.readAll()
    }

    @GET
    @Path("/{id}")
    fun getLog(@PathParam("id") id: String): Logging {
        val existingLog = loggingService.readById(id)
        if (existingLog == null) {
            throw NotFoundException("Log with ID $id not found.")
        }
        return existingLog
    }

    @GET
    @Path("/paged/{page}")
    fun paged(@PathParam("page") page: Int): List<Logging>? {
        val pagedLogs: PanacheQuery<Logging> = loggingService.findAll()

        val totalPages = ceil(pagedLogs.count().toDouble() / TOTAL_PER_PAGE).toInt()
        if (page < 1 || page > totalPages) {
            throw NotFoundException("Page $page not found.")
        }
        return pagedLogs.page(page - 1, TOTAL_PER_PAGE).list() ?: throw Exception("Failed to retrieve paginated logs.")
    }

    @DELETE
    @Path("/{id}")
    fun delete(@PathParam("id") id: String): Logging {
        val log = loggingService.readById(id)
        if (log == null) {
            throw NotFoundException("Log with ID $id not found.")
        }
        loggingService.deleteById(id)
        return log
    }

    @DELETE
    fun deleteAll(): List<Logging> {
        loggingService.deleteAll()
        return emptyList()
    }
}
