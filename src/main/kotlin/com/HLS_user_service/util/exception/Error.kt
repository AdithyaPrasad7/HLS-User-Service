package com.HLS_user_service.util.exception

class Error {
    var resource: String? = null
    var field: String? = null
    var code: String? = null
    var message: String? = null
    var details: String? = null

    constructor(resource: String, field: String, code: String, message: String, details: String) {
        this.resource = resource
        this.field = field
        this.code = code
        this.message = message
        this.details = details
    }
}

