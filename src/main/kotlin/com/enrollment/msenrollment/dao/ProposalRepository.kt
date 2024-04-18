package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.Proposal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProposalRepository : JpaRepository<Proposal, Long>{
}