package com.HLS_user_service.util

import java.util.Date
import java.util.concurrent.TimeUnit

fun <T> T?.notNull(): T {
    return this!!
}

fun Date.addDays(days: Long): Date {
    return Date(this.time + TimeUnit.DAYS.toMillis(days))
}