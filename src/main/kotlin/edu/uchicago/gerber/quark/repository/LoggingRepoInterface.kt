package edu.uchicago.gerber.quark.repository

import edu.uchicago.gerber.quark.models.Logging
import io.quarkus.mongodb.panache.kotlin.PanacheQuery

interface LoggingRepoInterface {
    fun _create(log: Logging)
    fun _create(logs: List<Logging>)
    fun _readById(id: String): Logging
    fun readAll(): List<Logging>
    fun _count(): Long
    fun _deleteById(id: String)
    fun _deleteAll()
    fun _findAll(): PanacheQuery<Logging>
}
