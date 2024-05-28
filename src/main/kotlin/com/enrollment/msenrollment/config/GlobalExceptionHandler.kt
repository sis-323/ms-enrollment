package com.enrollment.msenrollment.config

import com.enrollment.msenrollment.exception.StudentNotAssignedException
import com.files.msfiles.dto.ResponseDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    companion object {
        private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java.name)
    }


    @ExceptionHandler(StudentNotAssignedException::class)
    fun handleStudentNotAssignedException(e: StudentNotAssignedException): ResponseEntity<ResponseDto<Nothing>> {
        return ResponseEntity(ResponseDto(null, e.message!!, false), HttpStatus.BAD_REQUEST)
    }
}