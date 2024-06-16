package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.CompletionLetter
import org.springframework.data.jpa.repository.JpaRepository

interface CompletionLetterRepository : JpaRepository<CompletionLetter, Long>{

    fun existsByFinalProjectIdProjectId(finalProjectId: Long): Boolean
}