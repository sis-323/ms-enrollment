package com.enrollment.msenrollment.bl

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
    @Autowired private val visitSessionRepository: VisitSessionRepository
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

    private fun toDate(date: String): Date {
        return SimpleDateFormat("yyyy-MM-dd").parse(date)
    }

}