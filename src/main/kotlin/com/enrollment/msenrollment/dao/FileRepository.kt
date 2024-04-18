package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.File
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FileRepository : JpaRepository<File, Long> {

    fun findByFileId(fileId: Long): File?
}