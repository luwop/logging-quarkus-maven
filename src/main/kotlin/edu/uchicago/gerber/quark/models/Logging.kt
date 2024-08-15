package edu.uchicago.gerber.quark.models

import org.bson.types.ObjectId

class Logging {
    var id: ObjectId? = null
    lateinit var sessionEmail: String
    lateinit var event: String
    var timeStamp: Long = 0L

}