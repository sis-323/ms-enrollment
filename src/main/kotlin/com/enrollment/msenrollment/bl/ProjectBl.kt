package com.enrollment.msenrollment.bl

import com.enrollment.msenrollment.dao.ProposalRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProjectBl
    (@Autowired private val proposalRepository: ProposalRepository) {

    fun approveProposal(proposalId: Long) {
        val proposal = proposalRepository.findById(proposalId).get()

    }
}