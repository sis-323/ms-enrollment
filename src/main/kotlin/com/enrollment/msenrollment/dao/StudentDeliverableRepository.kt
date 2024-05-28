package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.Assignation
import com.enrollment.msenrollment.entity.StudentDeliverable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface StudentDeliverableRepository: JpaRepository<StudentDeliverable, Long>{

    fun findByAssignation(assignationId: Assignation): List<StudentDeliverable>

        @Query("SELECT sd FROM StudentDeliverable sd WHERE sd.assignation = (SELECT p.assignationId FROM Project p WHERE p.projectId = :projectId)")
    fun findByProjectId(projectId: Long): List<StudentDeliverable>
}