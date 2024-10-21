package com.enrollment.msenrollment.dto

data class ProposalDetailDto (
    val proposalId: Long,
    val proposalName: String,
    val email: String,
    val fullName: String,
    val requirements: List<RequirementDto>,
    val proposalFile: String,
    val proposalStatus: String,
    val proposalFileName: String,
    val observation: String,
    val observationAuthor: String
)

data class RequirementDto(

    val requirementName: String,
    val requirementLink: String,

)