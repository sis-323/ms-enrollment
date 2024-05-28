package com.enrollment.msenrollment.dto

data class DeliverableDto(
    val deliverableId: Long? = null,
    val title: String,
    val dueDate: String,
    val description: String,
    val fileUrl: String? = null,
    val status: String? = null
    )