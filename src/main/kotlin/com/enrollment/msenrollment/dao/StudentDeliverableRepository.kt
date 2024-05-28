package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.Assignation
import com.enrollment.msenrollment.entity.StudentDeliverable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentDeliverableRepository: JpaRepository<StudentDeliverable, Long>{

    fun findByAssignation(assignationId: Assignation): List<StudentDeliverable>
}