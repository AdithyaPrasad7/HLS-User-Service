package com.HLS_user_service.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
open class Response {
    var data: Any? = null
    var errors: Any? = null
    var success: Boolean = false
    var statusMessage: String? = null
    var contentType = "application/json; charset=utf-8"
    var httpStatusCode: Int = 200
    var meta: MetaType? = null

    constructor()

    fun data(data: Any?): Response {
        this.data = data
        return this
    }

    fun errors(errors: Any): Response {
        this.errors = errors
        return this
    }

    fun contentType(contentType: String): Response {
        this.contentType = contentType
        return this
    }

    fun statusMessage(statusMessage: String): Response {
        this.statusMessage = statusMessage
        return this
    }

    fun success(): Response {
        return success(true)
    }

    fun success(success: Boolean): Response {
        this.success = success
        return this
    }

    fun fail(): Response {
        return success(false)
    }

    fun httpStatusCode(httpStatusCode: Int): Response {
        this.httpStatusCode = httpStatusCode
        return this
    }

    fun meta(metaType: MetaType): Response {
        this.meta = metaType
        return this
    }
}

open class MetaType {
    var type: String
    constructor(type: String) {
        this.type = type
    }
}
