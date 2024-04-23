package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.Deliverable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeliverableRepository : JpaRepository<Deliverable, Long> {
}