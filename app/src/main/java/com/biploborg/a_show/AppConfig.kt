package com.biploborg.a_show

import java.util.Calendar

object AppConfig {
    const val EXAM_NAME = "HSC Exam 2026"
    
    // July 2nd, 2026
    val EXAM_DATE: Calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, 2026)
        set(Calendar.MONTH, Calendar.JULY)
        set(Calendar.DAY_OF_MONTH, 2)
        set(Calendar.HOUR_OF_DAY, 10) // Assuming 10 AM
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }
}
