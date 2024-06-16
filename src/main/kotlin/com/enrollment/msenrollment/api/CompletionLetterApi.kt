package com.enrollment.msenrollment.api

import com.enrollment.msenrollment.bl.CompletionLetterBl
import com.enrollment.msenrollment.dto.CompletionLetterDto
import com.files.msfiles.dto.ResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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


}