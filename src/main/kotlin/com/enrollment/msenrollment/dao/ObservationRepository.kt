package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.Observation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ObservationRepository:JpaRepository<Observation, Long> {
}