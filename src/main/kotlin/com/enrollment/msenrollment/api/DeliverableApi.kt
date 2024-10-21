package com.enrollment.msenrollment.api

import com.enrollment.msenrollment.bl.DeliverableBl
import com.enrollment.msenrollment.dto.DeliverableDto
import com.files.msfiles.dto.FileDto
import com.files.msfiles.dto.ResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/deliverable")
class DeliverableApi (
    @Autowired private val deliverableBl: DeliverableBl
){

    @PostMapping("/{fileId}")
    fun createDeliverable(@RequestBody deliverableDto: DeliverableDto,
                          @PathVariable fileId: Long): ResponseEntity<ResponseDto<String>> {
        deliverableBl.saveDeliverable(deliverableDto,fileId)
        return ResponseEntity.ok(ResponseDto(null,
                "Deliverable created successfully",
                true))

    }

    @GetMapping("/")
    fun getDeliverables(): ResponseEntity<ResponseDto<List<DeliverableDto>>> {
        val deliverables = deliverableBl.findDeliverables()
        return ResponseEntity.ok(ResponseDto(deliverables, "Deliverables retrieved", true))
    }

    @PostMapping("/student/{studentKcId}/deliverable/{deliverableId}")
    fun uploadStudentDeliverable(@PathVariable studentKcId: String,
                                 @PathVariable deliverableId: Long,
                                 @RequestBody fileDto: FileDto):ResponseEntity<ResponseDto<String>> {
        deliverableBl.saveStudentDeliverable(fileDto,studentKcId, deliverableId)
        return ResponseEntity.ok(ResponseDto(null,
                "Deliverable uploaded successfully",
                true))
    }

    @GetMapping("/{projectId}/student/deliverables")
    fun getStudentDeliverables(@PathVariable projectId:Long): ResponseEntity<ResponseDto<List<DeliverableDto>>>
    {
        val deliverables = deliverableBl.findDeliverablesByProjectId( projectId)
        return ResponseEntity.ok(ResponseDto(deliverables, "Deliverables retrieved", true))
    }
    @GetMapping("/pending/")
        fun getPendingDeliverables(
            @RequestParam("kcId") kcId: String): ResponseEntity<ResponseDto<List<DeliverableDto>>> {
            val deliverables = deliverableBl.findPendingDeliverables(kcId)
            if (deliverables.isEmpty()) {
                return ResponseEntity.ok(ResponseDto(deliverables, "No pending deliverables", true))
            }
            return ResponseEntity.ok(ResponseDto(deliverables, "Deliverables retrieved", true))
        }

    @GetMapping("/student/{kcId}/deliverables")
    fun getStudentDeliverables(@PathVariable kcId: String): ResponseEntity<ResponseDto<List<DeliverableDto>>>
    {
        val deliverables = deliverableBl.findDeliverablesByStudentId(kcId)
        return ResponseEntity.ok(ResponseDto(deliverables, "Deliverables retrieved", true))
    }

}