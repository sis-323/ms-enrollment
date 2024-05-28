package com.enrollment.msenrollment.api

import com.enrollment.msenrollment.bl.EnrollmentBl
import com.enrollment.msenrollment.dto.SaveProposalDto
import com.files.msfiles.dto.FileDto
import com.files.msfiles.dto.ProposalDto
import com.files.msfiles.dto.ResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/enrollments")
class EnrollmentApi constructor(
        private val enrollmentBl: EnrollmentBl
){
    @PostMapping("/")
    fun saveProposal(@RequestBody requestDto: SaveProposalDto):
            ResponseEntity<ResponseDto<String>> {
        enrollmentBl.saveEnrollment(requestDto.fileDto, requestDto.proposalDto, requestDto.requirements)
        return ResponseEntity.ok(ResponseDto(null, "Proposal created", true))
    }



}