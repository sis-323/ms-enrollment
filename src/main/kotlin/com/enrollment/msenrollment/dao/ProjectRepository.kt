package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.Assignation
import com.enrollment.msenrollment.entity.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository : JpaRepository<Project, Long>{

    fun findByAssignationId(assignationId: Assignation): Project
}