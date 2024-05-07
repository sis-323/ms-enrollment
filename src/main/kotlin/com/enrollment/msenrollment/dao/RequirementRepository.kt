package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.Person
import com.enrollment.msenrollment.entity.Proposal
import com.enrollment.msenrollment.entity.Requirement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RequirementRepository : JpaRepository<Requirement, Long> {

    fun findRequirementsByPerson(user: Person): List<Requirement>
}