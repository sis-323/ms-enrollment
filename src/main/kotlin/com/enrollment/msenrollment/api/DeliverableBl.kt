package com.enrollment.msenrollment.api

import com.enrollment.msenrollment.dto.DeliverableDto
import com.files.msfiles.dto.ResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/deliverable")
class DeliverableBl {

    @PostMapping("/")
    fun createDeliverable(@RequestParam("file") file: MultipartFile,
        @RequestParam("title") title: String,
        @RequestParam("dueDate") dueDate: String,
        @RequestParam("description") description: String): ResponseEntity<ResponseDto<String>> {
        return ResponseEntity.ok(ResponseDto(null,
                "Deliverable created successfully",
                true))

    }

}