package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.Assignation
import com.enrollment.msenrollment.entity.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ProjectRepository : JpaRepository<Project, Long>{

    fun findAllByStatusFalse(): List<Project>


    fun findByAssignationId(assignationId: Assignation): Optional<Project>
}