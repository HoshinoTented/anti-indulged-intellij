import kotlinx.coroutines.runBlocking
import org.hoshino9.anti.indulged.core.Clock
import org.hoshino9.anti.indulged.core.DefaultAntiIndulged
import org.hoshino9.anti.indulged.core.LimitationReminder
import org.junit.Test
import kotlin.system.measureTimeMillis

class TimerTest {
    object ConsoleReminder : LimitationReminder {
        override fun remind(rest: Long): Boolean {
            println(rest)
            return false
        }
    }

    data class LimitData(override var time: Long) : Clock {
        override val cycle: Long
            get() = 15 * 1000
        override val rest: Long
            get() = 5 - time

        override fun increase() {
            time += 1
        }

    }

    @Test
    fun timer() {
        measureTimeMillis {
            val anti = DefaultAntiIndulged(LimitData(0), ConsoleReminder)
            anti.startTiming()
            runBlocking {
                anti.join()
            }
        }.run(::println)
    }
}