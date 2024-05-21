package com.enrollment.msenrollment.api

import com.enrollment.msenrollment.bl.ProjectBl
import com.enrollment.msenrollment.dto.ModalityDto
import com.enrollment.msenrollment.dto.ProposalDetailDto
import com.enrollment.msenrollment.dto.ProposalOutDto
import com.files.msfiles.dto.ResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/projects")
@CrossOrigin(origins = ["*"])
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

    @GetMapping("/proposals")
    fun getProposals(): ResponseEntity<ResponseDto<List<ProposalOutDto>>> {
        val proposals = projectBl.findProposals()
        return ResponseEntity.ok(ResponseDto(proposals, "Proposals retrieved", true))

    }

    @GetMapping("/proposals/{studentKcId}")
    fun getProposalByStudentKcId(@PathVariable studentKcId: String): ResponseEntity<ResponseDto<ProposalOutDto>> {
        val proposal = projectBl.findProposalByStudentKcId(studentKcId)
        return ResponseEntity.ok(ResponseDto(proposal, "Proposal retrieved", true))
    }

    @GetMapping("/proposals/{studentKcId}/details")
    fun getProposalDetailByStudentKcId(@PathVariable studentKcId: String):
            ResponseEntity<ResponseDto<ProposalDetailDto>> {
        val proposal = projectBl.findProposalDetailByStudentKcId(studentKcId)
        return ResponseEntity.ok(ResponseDto(proposal, "Proposal retrieved", true))
    }

    @GetMapping("/modalities")
    fun getModalities(): ResponseEntity<ResponseDto<List<ModalityDto>>> {
        val modalities = projectBl.findModalities()
        return ResponseEntity.ok(ResponseDto(modalities, "Modalities retrieved", true))
    }


}