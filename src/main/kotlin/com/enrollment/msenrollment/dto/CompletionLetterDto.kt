package com.enrollment.msenrollment.dto

import java.sql.Timestamp
import java.util.*

data class CompletionLetterDto (
    var fileId: Long,
    var finalProjectId: Long,
    var date: Date,
    var time: Timestamp,
    var approvedByTutor: Boolean,
    var approvedByRelator: Boolean
)