package com.enrollment.msenrollment.dto

data class VisitSessionDto(
    val visitSessionId: Long,
    val visitDate: String,
    val observation: String?,
    val didStudentAttend: Boolean?
)