package com.enrollment.msenrollment.api

import com.enrollment.msenrollment.bl.ProjectBl

import com.enrollment.msenrollment.dto.*
import com.enrollment.msenrollment.entity.SearchProject

import com.enrollment.msenrollment.dto.ModalityDto
import com.enrollment.msenrollment.dto.ObservationDto
import com.enrollment.msenrollment.dto.ProposalDetailDto
import com.enrollment.msenrollment.dto.ProposalOutDto
import com.enrollment.msenrollment.exception.StudentNotAssignedException

import com.files.msfiles.dto.ResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/projects")
class ProjectsApi (
        private val projectBl: ProjectBl
){

    @PutMapping("/{proposalId}/approve")
    fun approveProposal( @PathVariable proposalId: Long): ResponseEntity<ResponseDto<String>> {
        try {
            projectBl.approveProposal(proposalId)
            return ResponseEntity.ok(ResponseDto(null, "Propuesta aprobada",
                true))
        }
        catch (e: StudentNotAssignedException) {
            return ResponseEntity.badRequest().body(ResponseDto(null, e.message!!, false))
        }
    }

    @PutMapping("/{proposalId}/reject")
    fun rejectProposal( @PathVariable proposalId: Long): ResponseEntity<ResponseDto<String>> {
        projectBl.rejectProposal(proposalId)
        return ResponseEntity.ok(ResponseDto(null, "Proposal rejected", true))
    }
    @PutMapping("/{proposalId}/observe/student/{studentKcId}")
    fun observeProposal( @PathVariable proposalId: Long, @PathVariable studentKcId: String,
                         @RequestBody observationDto: ObservationDto
                         ): ResponseEntity<ResponseDto<String>> {
        projectBl.reviewProposal(studentKcId, proposalId, observationDto)
        return ResponseEntity.ok(ResponseDto(null, "Proposal observed", true))
    }



    @GetMapping("/proposals")
    fun getProposals(): ResponseEntity<ResponseDto<List<ProposalOutDto>>> {
        val proposals = projectBl.findProposals()
        return ResponseEntity.ok(ResponseDto(proposals, "Proposals retrieved", true))

    }

    @GetMapping("/proposals/{studentKcId}")
    fun getProposalByStudentKcId(@PathVariable studentKcId: String):
            ResponseEntity<ResponseDto<List<ProposalOutDto>>> {
        val proposal = projectBl.findProposalByStudentKcId(studentKcId)
        return ResponseEntity.ok(ResponseDto(proposal, "Proposal retrieved", true))
    }

    @GetMapping("/proposals/{proposalId}/{studentKcId}/details")
    fun getProposalDetailByStudentKcId(@PathVariable studentKcId: String, @PathVariable proposalId: Long):
            ResponseEntity<ResponseDto<ProposalDetailDto>> {
        val proposal = projectBl.findProposalDetailByStudentKcId(studentKcId, proposalId)
        return ResponseEntity.ok(ResponseDto(proposal, "Proposal retrieved", true))
    }

    @GetMapping("/modalities")
    fun getModalities(): ResponseEntity<ResponseDto<List<ModalityDto>>> {
        val modalities = projectBl.findModalities()
        return ResponseEntity.ok(ResponseDto(modalities, "Modalities retrieved", true))
    }

    @GetMapping("/final")
    fun getFinalProjects(): ResponseEntity<ResponseDto<List<SearchProjectResponseDto>>> {
        val projects = projectBl.findFinalProjects();
        return ResponseEntity.ok(ResponseDto(projects, "Projects retrieved", true))
    }

    @GetMapping("/pending")
    fun getPendingProjects(): ResponseEntity<ResponseDto<List<SearchProjectResponseDto>>> {
        val projects = projectBl.findPendingProjects();
        return ResponseEntity.ok(ResponseDto(projects, "Projects retrieved", true))
    }

    @GetMapping("/search")
    fun searchProposals(@RequestParam("keywords") keywords: String): ResponseEntity<ResponseDto<List<SearchProjectResponseDto>>> {
        val projects = projectBl.searchProjects(keywords);
        return ResponseEntity.ok(ResponseDto(projects, "Projects retrieved", true))
    }

}