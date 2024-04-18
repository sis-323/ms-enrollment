package com.files.msfiles.dto

data class ResponseDto<T>(
        val data: T?,
        val message: String,
        val successful: Boolean
)