package com.enrollment.msenrollment.bl

import com.enrollment.msenrollment.dao.AssignationRepository
import com.enrollment.msenrollment.dao.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AssignationBl constructor(
    @Autowired private val assignationRepository: AssignationRepository,
    @Autowired private val userRepository: PersonRepository
){

    fun findAssignationByStudentId(studentKcId: String): Any{
        val student = userRepository.findByIdKc(studentKcId)
        return assignationRepository.findByStudentId(student)
    }





    /**
     * this method is used to verify the assignation of a student
     * by their keycloak id
     */
    fun verifyAssignation(keycloakId: String): Boolean {
        val student = userRepository.findByIdKc(keycloakId)
        return assignationRepository.existsByStudentId(student)

    }

}