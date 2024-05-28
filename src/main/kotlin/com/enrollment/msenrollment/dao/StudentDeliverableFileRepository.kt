package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.StudentDeliverableFile
import org.springframework.data.jpa.repository.JpaRepository

interface StudentDeliverableFileRepository : JpaRepository<StudentDeliverableFile, Long>{
}