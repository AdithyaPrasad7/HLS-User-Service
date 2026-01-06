package com.HLS_user_service.util

fun <T> T?.notNull(): T {
    return this!!
}