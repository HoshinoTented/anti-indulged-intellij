package org.hoshino9.anti.indulged.core

interface Logger {
    object EMPTY : Logger {
        override fun info(message: String) {
        }

        override fun warn(message: String) {
        }

        override fun error(message: String) {
        }

    }

    fun info(message: String)
    fun warn(message: String)
    fun error(message: String)
}

object ConsoleLogger : Logger {
    override fun info(message: String) {
        println(message)
    }

    override fun warn(message: String) {
        println(message)
    }

    override fun error(message: String) {
        println(message)
    }

}