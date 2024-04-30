package com.enrollment.msenrollment.dto

data class ProposalOutDto(
    val proposalId: Long,
    val title: String,
    val uploadedBy: String,
    val proposalStatus: String,
    val fileUrl: String
)