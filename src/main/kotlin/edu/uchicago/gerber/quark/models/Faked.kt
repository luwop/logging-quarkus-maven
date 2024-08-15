package edu.uchicago.gerber.quark.models

import com.github.javafaker.Faker
import org.bson.types.ObjectId
import edu.uchicago.gerber.quark.models.Logging

object Faked {
    // Static properties
    val faker = Faker()
    val FAKE_ID = "5063114bd386d8fadbd6b004"

    fun genRawEntity(): Logging {
        val log = Logging()
        log.sessionEmail = faker.internet().emailAddress()
        log.event = faker.lorem().sentence()
        log.timeStamp = System.currentTimeMillis()
        return log
    }

    fun genFakerLogging(num: Int): List<Logging> {
        val list = mutableListOf<Logging>()
        repeat(num) { list.add(genRawEntity()) }
        return list
    }

    // Use a string such as "5063114bd386d8fadbd6b004"
    fun genTestLogging(hash: String): Logging {
        val log = genRawEntity()
        log.id = ObjectId(hash)
        return log
    }
}
