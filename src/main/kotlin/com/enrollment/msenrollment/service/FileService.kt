package com.enrollment.msenrollment.service

import com.files.msfiles.dto.ResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile


@FeignClient(name = "ms-files")
interface FileService {


    @PostMapping("/api/v1/files/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadFile(@RequestParam("files") files: List<MultipartFile>): List<String>

    @GetMapping("/api/v1/files/url")
    fun getFileUrl(@RequestParam("fileName") fileName: String): ResponseEntity<ResponseDto<String>>
}



