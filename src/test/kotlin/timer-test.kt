import kotlinx.coroutines.runBlocking
import org.hoshino9.anti.indulged.core.DefaultAntiIndulged
import org.hoshino9.anti.indulged.core.LimitData
import org.hoshino9.anti.indulged.core.LimitationReminder
import org.junit.Test
import kotlin.system.measureTimeMillis

class TimerTest {
    object ConsoleReminder : LimitationReminder {
        override fun remind(level: LimitationReminder.Level) {
            println(level)
        }
    }

    @Test
    fun timer() {
        measureTimeMillis {
            val anti = DefaultAntiIndulged(LimitData(0, 5), ConsoleReminder)
            anti.startTiming()
            runBlocking {
                anti.join()
            }
        }.run(::println)
    }
}