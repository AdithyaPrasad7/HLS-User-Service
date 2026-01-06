package com.HLS_user_service.util

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequest : RuntimeException {
    var classType: String = ""
    var fieldError: String = ""
    constructor(message: String, ex: Exception?): super(message, ex)
    constructor(message: String): super(message)
    constructor(ex: Exception): super(ex)
    constructor(message: String, classType: String, fieldError: String): super(message) {
        this.classType = classType
        this.fieldError = fieldError
    }
}