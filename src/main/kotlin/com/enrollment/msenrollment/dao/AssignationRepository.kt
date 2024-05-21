package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.Assignation
import com.enrollment.msenrollment.entity.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AssignationRepository : JpaRepository<Assignation, Long> {

    fun findByStudentId(studentId: Person): Assignation

    fun findByStudentIdIdKc(studentKcId: String): Assignation

    fun findAllByTutorId(tutorId: Person): List<Assignation>

    fun existsByStudentId(studentId: Person): Boolean

    fun findByStudentIdAndRelatorId(studentId: Person, relatorId: Person): Assignation


}