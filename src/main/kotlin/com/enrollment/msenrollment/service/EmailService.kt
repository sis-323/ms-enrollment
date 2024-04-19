package com.enrollment.msenrollment.service

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "ms-notification")

interface EmailService {

    @PostMapping("/api/v1/email/")
    fun sendEmail(@RequestParam("userKc") userKcId: String,
                  @RequestParam("type") type: String)
}