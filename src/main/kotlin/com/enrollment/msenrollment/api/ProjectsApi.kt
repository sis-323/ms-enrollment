package com.enrollment.msenrollment.api

import com.enrollment.msenrollment.bl.ProjectBl
import com.files.msfiles.dto.ResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/projects")
class ProjectsApi (
        private val projectBl: ProjectBl
){

    @PutMapping("/{proposalId}/approve")
    fun approveProposal( @PathVariable proposalId: Long): ResponseEntity<ResponseDto<String>> {
        projectBl.approveProposal(proposalId)
        return ResponseEntity.ok(ResponseDto(null, "Proposal approved", true))
    }

    @PutMapping("/{proposalId}/reject")
    fun rejectProposal( @PathVariable proposalId: Long): ResponseEntity<ResponseDto<String>> {
        projectBl.rejectProposal(proposalId)
        return ResponseEntity.ok(ResponseDto(null, "Proposal rejected", true))
    }


}