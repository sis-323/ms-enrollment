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
){

    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(VisitSessionBl::class.java.name)
    }

    fun saveVisitSession(visitSessionDto: VisitSessionDto, studentKcId: String){
        logger.info("Saving visit session")
        val assignationExists = assignationBl.verifyAssignation(studentKcId)
        if(assignationExists){
            val assignation = assignationBl.findAssignationByStudentId(studentKcId) as Assignation
            logger.info("Saving visit session for student with keycloak id: $studentKcId")
            val visitSession = VisitSession(
                date = toDate(visitSessionDto.visitDate),
                observation = visitSessionDto.observation,
                assignation = assignation,
                studentAssisted = null,
                status = true,

            )
            visitSessionRepository.save(visitSession)
            logger.info("Visit session saved successfully")
        }
        else{
            logger.error("Assignation not found for student with keycloak id: $studentKcId")
            throw Exception("Assignation not found")
        }



    }

    fun findSessionsByTutorId(tutorKcId: String): List<VisitSessionDto>{
        logger.info("Finding sessions by tutor kc id: $tutorKcId")
        val sessions = visitSessionRepository.findAllByAssignationTutorIdIdKc(tutorKcId)
        val sessionsDto = mutableListOf<VisitSessionDto>()
        sessions.forEach {
            sessionsDto.add(VisitSessionDto(
                visitSessionId = it.id!!,
                visitDate = SimpleDateFormat("yyyy-MM-dd").format(it.date),
                observation = it.observation,
                didStudentAttend = it.studentAssisted,
            ))
        }
        return sessionsDto
    }

    fun findSessionById(sessionId: Long): VisitSessionDto {
        logger.info("Finding session by id: $sessionId")
        val session = visitSessionRepository.findById(sessionId).get()
        return VisitSessionDto(
            visitSessionId = session.id!!,
            visitDate = SimpleDateFormat("yyyy-MM-dd").format(session.date),
            observation = session.observation,
            didStudentAttend = session.studentAssisted,
        )
    }
    fun updateSession(visitSessionDto: VisitSessionDto, sessionId: Long) {
        logger.info("Updating session with id: $sessionId")
        val session = visitSessionRepository.findById(sessionId).get()
        session.observation = visitSessionDto.observation
        session.studentAssisted = visitSessionDto.didStudentAttend
        visitSessionRepository.save(session)
    }

    private fun toDate(date: String): Date {
        return SimpleDateFormat("yyyy-MM-dd").parse(date)
    }

}