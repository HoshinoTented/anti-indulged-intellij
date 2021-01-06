package org.hoshino9.anti.indulged.core

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DefaultAntiIndulged(
    private val clock: Clock,
    private val factory: ReminderFactory,
    private val logger: Logger
) : AntiIndulged, CoroutineScope {
    private var timer: Job? = null

    override val isActive: Boolean
        get() = timer?.isActive == true

    @Synchronized
    override fun startTiming() {
        logger.info("Trying to start timing...")

        if (timer?.isActive == true) {
            logger.info("Timer has been started.")

            return
        }

        timer = launch(this.coroutineContext) {
            while (isActive) {
                val clock = coroutineContext[Clock.Key] ?: throw NoSuchElementException("Clock.Key not found")

                logger.info(clock.toString())
                val reminder = factory.newInstance(clock)

                if (reminder.shouldClose) {
                    stopTiming()
                    reminder.remind()
                    break
                }

                clock.increase()
                reminder.remind()

                delay(clock.cycle)
            }
        }

        logger.info("Started")
    }

    @Synchronized
    override fun stopTiming() {
        logger.info("Trying to stop timing")

        val timer = timer ?: return

        timer.cancel()
        logger.info("Stopped")

        this.timer = null
    }

    suspend fun join() {
        if (timer?.isActive == true) {
            timer?.join()
        }
    }

    override val coroutineContext: CoroutineContext
        get() = clock
}