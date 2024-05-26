package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.VisitSession
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface VisitSessionRepository : JpaRepository<VisitSession, Long> {

    fun findAllByAssignationTutorIdIdKc(tutorId: String): List<VisitSession>

    fun findAllByAssignationStudentIdIdKc(studentId: String): List<VisitSession>
}