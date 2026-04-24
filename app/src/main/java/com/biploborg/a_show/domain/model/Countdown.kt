package com.biploborg.a_show.domain.model

data class Countdown(
    val days: Long,
    val hours: Long,
    val minutes: Long,
    val seconds: Long,
    val isFinished: Boolean = false
)
