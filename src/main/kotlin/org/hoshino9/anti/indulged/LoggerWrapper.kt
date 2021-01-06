package org.hoshino9.anti.indulged

import com.intellij.openapi.diagnostic.Logger as IJLogger
import org.hoshino9.anti.indulged.core.Logger as AILogger

class LoggerWrapper(val logger: IJLogger) : AILogger {
    override fun info(message: String) {
        logger.info(message)
    }

    override fun warn(message: String) {
        logger.warn(message)
    }

    override fun error(message: String) {
        logger.error(message)
    }
}