package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.StudentRequirement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRequirementRepository : JpaRepository<StudentRequirement, Long> {
}