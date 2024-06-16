package com.enrollment.msenrollment.api

import com.enrollment.msenrollment.bl.DefenseBl
import com.files.msfiles.dto.ResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/defense")
class DefenseApi (
    @Autowired private val defenseBl: DefenseBl
) {
    @GetMapping("/verifyCompletionLetter/{projectId}")
    fun verifyCompletionLetter(@PathVariable projectId: Long): ResponseEntity<ResponseDto<Boolean>> {
        return ResponseEntity.ok(ResponseDto(data = defenseBl.verifyCompletionLetter(projectId),
            message = "Completion letter verified",
           successful = true))
    }

}