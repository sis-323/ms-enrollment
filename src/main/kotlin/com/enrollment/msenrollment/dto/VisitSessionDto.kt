package com.enrollment.msenrollment.dto

import java.util.*

data class VisitSessionDto(
    val visitSessionId: Long,
    val visitDate: Date?,
    val observation: String?,
    val didStudentAttend: Boolean?,
    val hour: Date?,
    val platform: String?,
    val link: String?,
    val performedAction: String?
)