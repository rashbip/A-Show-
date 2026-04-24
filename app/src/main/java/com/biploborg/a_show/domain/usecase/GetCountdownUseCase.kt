package com.biploborg.a_show.domain.usecase

import com.biploborg.a_show.domain.model.Countdown
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class GetCountdownUseCase {
    fun execute(): Countdown {
        val zoneId = ZoneId.of("Asia/Dhaka")
        val now = LocalDateTime.now(zoneId)
        
        // Explicitly set target date to July 2, 2026
        val target = LocalDateTime.of(2026, 7, 2, 10, 0)

        // Using ChronoUnit for more direct day counting
        val totalDays = ChronoUnit.DAYS.between(now.toLocalDate(), target.toLocalDate())
        
        val duration = Duration.between(now, target)

        return if (duration.isNegative || duration.isZero) {
            Countdown(0, 0, 0, 0, isFinished = true)
        } else {
            Countdown(
                days = totalDays,
                hours = duration.toHours() % 24,
                minutes = duration.toMinutes() % 60,
                seconds = duration.getSeconds() % 60
            )
        }
    }

    fun getBanglaNumber(number: Long): String {
        val banglaDigits = listOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')
        return number.toString().map { 
            if (it.isDigit()) banglaDigits[it.toString().toInt()] else it
        }.joinToString("")
    }
}
