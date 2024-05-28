package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.DeliverableFile
import com.enrollment.msenrollment.entity.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeliverableFileRepository : JpaRepository<DeliverableFile, Long> {

    fun findAllByFinalProjectId(finalProjectId: Project): List<DeliverableFile>
}