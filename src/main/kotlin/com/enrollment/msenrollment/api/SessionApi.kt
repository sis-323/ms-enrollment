package com.enrollment.msenrollment.api

import com.enrollment.msenrollment.bl.VisitSessionBl
import com.enrollment.msenrollment.dto.VisitSessionDto
import com.files.msfiles.dto.ResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/sessions")
class SessionApi (
    @Autowired private val visitSessionBl: VisitSessionBl
) {
    companion object{
        private val logger = org.slf4j.LoggerFactory.getLogger(SessionApi::class.java)
    }

    @PostMapping("/")
    fun createSession(@RequestParam("studentKcId") studentKcId: String,
        @RequestBody visitSessionDto: VisitSessionDto): ResponseEntity<ResponseDto<String>> {
        try {
            visitSessionBl.saveVisitSession(visitSessionDto, studentKcId)
            return ResponseEntity.ok(ResponseDto(null, "Session created", true))
        }
        catch (e: Exception) {
            logger.error("Error creating session: ${e.message}")
            return ResponseEntity.internalServerError().body(ResponseDto(null, e.message!!, false))
        }
    }

    @GetMapping("/student/")
    fun getStudentSessions(
        @RequestParam("studentKcId") studentKcId: String,
        @RequestParam("attendance", required = false, defaultValue = "all") attendance: String? = "all",
        @RequestParam("date", required = false) date: String? = null
    ): ResponseEntity<ResponseDto<List<VisitSessionDto>>> {
        if (attendance != "attended" && attendance != "not-attended" && attendance != "pending" && attendance != "all") {
            return ResponseEntity.badRequest().body(ResponseDto(null, "Invalid attendance filter", false))
        }
        val studentSessions = visitSessionBl.findSessionsByStudentId(studentKcId, attendance,date)
        if (studentSessions.isEmpty()) {
            return ResponseEntity.ok(ResponseDto(studentSessions, "No sessions found", true))
        }
        return ResponseEntity.ok(ResponseDto(studentSessions, "Student sessions found", true))
    }

    @GetMapping("/tutor/")
    fun getTutorSessions(
        @RequestParam("tutorKcId") tutorKcId: String,
        @RequestParam("attendance", required = false, defaultValue = "all") attendance: String? = "all"

    ): ResponseEntity<ResponseDto<List<VisitSessionDto>>> {
        if (attendance != "attended" && attendance != "not-attended" && attendance != "pending" && attendance != "all") {
            return ResponseEntity.badRequest().body(ResponseDto(null, "Invalid attendance filter", false))
        }
        val tutorSessions = visitSessionBl.findSessionsByTutorId(tutorKcId, attendance)
        if (tutorSessions.isEmpty()) {
            return ResponseEntity.ok(ResponseDto(tutorSessions, "No sessions found", true))
        }
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