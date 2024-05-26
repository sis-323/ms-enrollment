package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.Proposal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProposalRepository : JpaRepository<Proposal, Long>{

    fun findAllByPersonIdKc(studentKcId: String): List<Proposal>

    @Query("SELECT p FROM Proposal p WHERE p.person.idKc = :studentKcId AND p.proposalId = :proposalId")
    fun findByPersonIdKcAndProposalId(@Param("studentKcId") studentKcId: String, @Param("proposalId") proposalId: Long): Proposal

    fun existsByPersonIdKc(studentKcId: String): Boolean

    }