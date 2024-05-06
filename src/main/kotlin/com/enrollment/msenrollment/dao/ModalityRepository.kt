package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.Modality
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ModalityRepository : JpaRepository<Modality, Long>{
}