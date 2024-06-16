package com.enrollment.msenrollment.api

import com.enrollment.msenrollment.bl.CompletionLetterBl
import com.enrollment.msenrollment.dto.CompletionLetterDto
import com.files.msfiles.dto.ResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/completionLetter")
class CompletionLetterApi (
    @Autowired val completionLetterBl: CompletionLetterBl
){

    @PostMapping("/save")
    fun saveCompletionLetter(@RequestBody completionLetterDto: CompletionLetterDto)
    :ResponseEntity<ResponseDto<String>>{
        completionLetterBl.saveCompletionLetter(completionLetterDto)
        return ResponseEntity.ok(ResponseDto
            (null, "Completion letter saved successfully", true))
    }

    @PutMapping("/approve/{projectId}/")
    fun approveCompletionLetter(@RequestParam("kcId") kcId: String,
                                @PathVariable("projectId") projectId: Long): ResponseEntity<ResponseDto<String>>{
        completionLetterBl.approveCompletionLetter(projectId, kcId)
        return ResponseEntity.ok(ResponseDto(null, "Completion letter approved successfully", true))

    }

    @GetMapping("/validate/{projectId}")
    fun validateCompletionLetter(@PathVariable("projectId") projectId: Long): ResponseEntity<ResponseDto<Boolean>>{
        val response = completionLetterBl.validateCompletionLetter(projectId)
        return ResponseEntity.ok(ResponseDto(response, "Completion letter validated successfully", true))
    }


}