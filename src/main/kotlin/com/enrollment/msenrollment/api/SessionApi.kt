package com.enrollment.msenrollment.api

import com.enrollment.msenrollment.bl.VisitSessionBl
import com.enrollment.msenrollment.dto.VisitSessionDto
import com.files.msfiles.dto.ResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/sessions")
@CrossOrigin(origins = ["*"])
class SessionApi (
    @Autowired private val visitSessionBl: VisitSessionBl
) {

    @PostMapping("/")
    fun createSession(@RequestParam("studentKcId") studentKcId: String,
        @RequestBody visitSessionDto: VisitSessionDto): ResponseEntity<ResponseDto<String>> {
        try {
            visitSessionBl.saveVisitSession(visitSessionDto, studentKcId)
            return ResponseEntity.ok(ResponseDto(null, "Session created", true))
        }
        catch (e: Exception) {
            return ResponseEntity.internalServerError().body(ResponseDto(null, e.message!!, false))
        }
    }

    @GetMapping("/tutor/")
    fun getTutorSessions(
        @RequestParam("tutorKcId") tutorKcId: String,
        @RequestParam("attendance", required = false) attendance: String? = ""
    ): ResponseEntity<ResponseDto<List<VisitSessionDto>>> {
        val tutorSessions = visitSessionBl.findSessionsByTutorId(tutorKcId, attendance!!)
        return ResponseEntity.ok(ResponseDto(tutorSessions, "Tutor sessions found", true))
    }

    @GetMapping("/{sessionId}")
    fun getSession(@PathVariable("sessionId") sessionId: Long): ResponseEntity<ResponseDto<VisitSessionDto>> {
        val session = visitSessionBl.findSessionById(sessionId)
        return ResponseEntity.ok(ResponseDto(session, "Session found", true))
    }

    @PutMapping("/{sessionId}")
    fun updateSession(@PathVariable("sessionId") sessionId: Long,
        @RequestBody visitSessionDto: VisitSessionDto): ResponseEntity<ResponseDto<String>> {
        try {
            visitSessionBl.updateSession(visitSessionDto,sessionId )
            return ResponseEntity.ok(ResponseDto(null, "Session updated", true))
        }
        catch (e: Exception) {
            return ResponseEntity.internalServerError().body(ResponseDto(null, e.message!!, false))
        }
    }

    @DeleteMapping("/{sessionId}")
    fun deleteSession(@PathVariable("sessionId") sessionId: Long): ResponseEntity<ResponseDto<String>> {
        try {
            visitSessionBl.deleteSession(sessionId)
            return ResponseEntity.ok(ResponseDto(null, "Session deleted", true))
        }
        catch (e: Exception) {
            return ResponseEntity.internalServerError().body(ResponseDto(null, e.message!!, false))
        }
    }
}