package com.enrollment.msenrollment.bl

import com.enrollment.msenrollment.dao.CompletionLetterRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DefenseBl (
    @Autowired private val completionLetterRepository: CompletionLetterRepository
) {

    fun verifyCompletionLetter(projectId: Long): Boolean {
        return completionLetterRepository.existsByFinalProjectIdProjectId(projectId)
    }


}