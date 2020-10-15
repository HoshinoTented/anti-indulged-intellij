import kotlinx.coroutines.runBlocking
import org.hoshino9.anti.indulged.core.Clock
import org.hoshino9.anti.indulged.core.DefaultAntiIndulged
import org.hoshino9.anti.indulged.core.ReminderFactory
import org.junit.Test
import kotlin.system.measureTimeMillis

class TimerTest {
    object ConsoleReminder : ReminderFactory {
        override fun newInstance(rest: Long): ReminderFactory.Reminder {
            println(rest)
            return ReminderFactory.Reminder.EMPTY
        }
    }

    data class LimitData(override var currentTime: Long) : Clock {
        override val cycle: Long
            get() = 15 * 1000
        override val rest: Long
            get() = 5 - currentTime

        override fun increase() {
            currentTime += 1
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

    @Test
    fun today() {
        println(org.hoshino9.anti.indulged.today)
    }
}