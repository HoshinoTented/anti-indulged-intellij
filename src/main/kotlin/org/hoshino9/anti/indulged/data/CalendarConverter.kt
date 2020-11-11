package org.hoshino9.anti.indulged.data

import java.util.*

object CalendarConverter : com.intellij.util.xmlb.Converter<Calendar>() {
    val DEFAULT: Calendar = fromDate(0L)

    private fun fromDate(date: Long): Calendar {
        val dAtE = Date(date)
        val calendar = Calendar.getInstance()
        calendar.time = dAtE

        return calendar
    }

    override fun toString(value: Calendar): String? {
        return value.timeInMillis.toString()
    }

    override fun fromString(value: String): Calendar? {
        return runCatching {
            val date = value.toLong()
            val calendar = fromDate(date)

            calendar.clearly()

            calendar
        }.getOrNull()
    }

    fun Calendar.clearly(): Calendar {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)

        return this
    }
}