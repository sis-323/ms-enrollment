package com.enrollment.msenrollment.bl

import com.enrollment.msenrollment.dao.AssignationRepository
import com.enrollment.msenrollment.dao.PersonRepository
import com.enrollment.msenrollment.dao.VisitSessionRepository
import com.enrollment.msenrollment.dto.VisitSessionDto
import com.enrollment.msenrollment.entity.Assignation
import com.enrollment.msenrollment.entity.VisitSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class VisitSessionBl constructor(
    @Autowired private val assignationBl: AssignationBl,
    @Autowired private val visitSessionRepository: VisitSessionRepository,
    @Autowired private val assignationRepository: AssignationRepository,
    @Autowired private val userRepository: PersonRepository
) {

    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(VisitSessionBl::class.java.name)
    }

    fun saveVisitSession(visitSessionDto: VisitSessionDto, studentKcId: String) {
        val assignationExists = assignationBl.verifyAssignation(studentKcId)
        if (assignationExists) {
            val assignation = assignationBl.findAssignationByStudentId(studentKcId) as Assignation
            logger.info("Assignation found: ${assignation.assignationId }")
            logger.info("Saving visit session for student with keycloak id: $studentKcId")
            val visitSession = VisitSession(
                date = visitSessionDto.visitDate!!,
                observation = null,
                assignation = assignation,
                studentAssisted = null,
                status = true,
                hour = visitSessionDto.hour!!,
                platform = visitSessionDto.platform!!,
                link = visitSessionDto.link!!,
                performedAction = visitSessionDto.performedAction!!

                )
            visitSessionRepository.save(visitSession)
            logger.info("Visit session saved successfully")
        } else {
            logger.error("Assignation not found for student with keycloak id: $studentKcId")
            throw Exception("Assignation not found")
        }


    }

    fun findSessionsByTutorId(tutorKcId: String, attendance: String): List<VisitSessionDto> {
        logger.info("Finding sessions by tutor kc id: $tutorKcId")

        val sessions = visitSessionRepository.findAllByAssignationTutorIdIdKc(tutorKcId)
        val filteredSessions = when (attendance) {
            "attended" -> sessions.filter { it.studentAssisted == true }
            "not-attended" -> sessions.filter { it.studentAssisted == false }
            "pending" -> sessions.filter { it.studentAssisted == null }
            "all" -> sessions
            else -> {
                logger.error("Invalid attendance filter")
                throw Exception("Invalid attendance filter")
            }
        }


        return filteredSessions.map {
            logger.info("Session date: ${it.date.toString()}")
            VisitSessionDto(
                visitSessionId = it.id!!,
                visitDate = it.date,
                observation = it.observation,
                didStudentAttend = it.studentAssisted,
                hour = it.hour,
                platform = it.platform,
                link = it.link,
                performedAction = it.performedAction
            )
        }}

    fun findSessionsByStudentId(studentKcId: String, attendance: String, date: String?): List<VisitSessionDto> {
        logger.info("Finding sessions by student kc id: $studentKcId")
        val sessions = visitSessionRepository.findAllByAssignationStudentIdIdKc(studentKcId)
        val filteredSessions = when (attendance) {
            "attended" -> sessions.filter { it.studentAssisted == true }
            "not-attended" -> sessions.filter { it.studentAssisted == false }
            "pending" -> sessions.filter { it.studentAssisted == null }
            "all" -> sessions
            else -> {
                logger.error("Invalid attendance filter")
                throw Exception("Invalid attendance filter")
            }
        }

        if (!date.isNullOrEmpty()) {
            logger.info("Filtering sessions by date: $date")
            filteredSessions.filter { it.date == toDate(date) }
        }

        return filteredSessions.map {
            VisitSessionDto(
                visitSessionId = it.id!!,
                visitDate = it.date,
                observation = it.observation,
                didStudentAttend = it.studentAssisted,
                hour = it.hour,
                platform = it.platform,
                link = it.link,
                performedAction = it.performedAction
            )
        }
    }

    fun findSessionById(sessionId: Long): VisitSessionDto {
        logger.info("Finding session by id: $sessionId")
        val session = visitSessionRepository.findById(sessionId).get()

        return VisitSessionDto(
            visitSessionId = session.id!!,
            visitDate = session.date,
            observation = session.observation,
            didStudentAttend = session.studentAssisted,
            hour = session.hour,
            platform = session.platform,
            link = session.link,
            performedAction = session.performedAction
        )
    }

    fun updateSession(visitSessionDto: VisitSessionDto, sessionId: Long) {
        logger.info("Updating session with id: $sessionId")
        val session = visitSessionRepository.findById(sessionId).get()
        session.observation = visitSessionDto.observation
        session.studentAssisted = visitSessionDto.didStudentAttend
        visitSessionRepository.save(session)
    }

    fun deleteSession(sessionId: Long) {
        logger.info("Deleting session with id: $sessionId")
        val session = visitSessionRepository.findById(sessionId).get()
        session.status = false
        visitSessionRepository.save(session)
        logger.info("Session deleted successfully")
    }

    private fun toDate(date: String): Date {
        return SimpleDateFormat("yyyy-MM-dd").parse(date)
    }
}




