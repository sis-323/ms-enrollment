package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.Proposal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProposalRepository : JpaRepository<Proposal, Long>{

    fun findAllByPersonIdKc(studentKcId: String): List<Proposal>

    fun findByPersonIdKcAndProposalId(studentKcId: String, proposalId: Long): Proposal

    fun existsByPersonIdKc(studentKcId: String): Boolean

    }