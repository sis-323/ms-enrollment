package com.enrollment.msenrollment.dto

import com.enrollment.msenrollment.entity.Proposal

import com.files.msfiles.dto.FileDto
import com.files.msfiles.dto.ProposalDto

data class SaveProposalDto (
        val fileDto: FileDto,
        val proposalDto: ProposalDto,
        val requirements : List<FileDto>
)