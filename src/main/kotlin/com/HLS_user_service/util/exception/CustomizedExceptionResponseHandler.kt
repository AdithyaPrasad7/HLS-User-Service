package com.HLS_user_service.util.exception

import com.HLS_user_service.dto.response.MetaType
import com.HLS_user_service.dto.response.Response
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
@RestController
@ResponseBody
class CustomizedExceptionResponseHandler : ResponseEntityExceptionHandler() {
    companion object {
        private val log = LoggerFactory.getLogger(CustomizedExceptionResponseHandler::class.java)
    }

    private var userType: String = "user"
    private val fail: String = "fail"

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        log.error("exception occurs", ex)

        val errors = mutableListOf<Error>()
        val errorDetails = Error(
            request.contextPath,
            ex.cause?.message ?: "",
            HttpStatus.INTERNAL_SERVER_ERROR.name,
            ex.message!!,
            request.getDescription(false)
        )
        errors.add(errorDetails)

        return ResponseEntity(
            buildResponse(request.contextPath, errors, HttpStatus.INTERNAL_SERVER_ERROR.value()),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(BadRequest::class)
    fun handleBadRequestException(ex: BadRequest, request: WebRequest):
            ResponseEntity<Response> {

        log.error("exception occurs", ex)

        val errors = mutableListOf<Error>()
        val errorDetails = Error(ex.classType, ex.fieldError, HttpStatus.BAD_REQUEST.name, ex.message!!,
            request.getDescription(false))

        errors.add(errorDetails)

        return ResponseEntity(buildResponse(ex.classType, errors, HttpStatus.BAD_REQUEST.value()),
            HttpStatus.BAD_REQUEST)
    }

    private fun buildResponse(metaType: String, errors: List<Error>, httpStatusCode: Int): Response {
        return Response().meta(MetaType(metaType))
            .errors(errors)
            .contentType("application/json")
            .fail().httpStatusCode(httpStatusCode).statusMessage(fail)
    }
}