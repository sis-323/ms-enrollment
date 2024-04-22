package com.enrollment.msenrollment.bl

import com.enrollment.msenrollment.dao.AssignationRepository
import com.enrollment.msenrollment.dao.EnrollmentRepository
import com.enrollment.msenrollment.dao.ProjectRepository
import com.enrollment.msenrollment.dao.ProposalRepository
import com.enrollment.msenrollment.entity.Project
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProjectBl
    (@Autowired private val proposalRepository: ProposalRepository,
            @Autowired private val assignationRepository: AssignationRepository,
            @Autowired private val projectRepository: ProjectRepository,
            @Autowired private val enrollmentRepository: EnrollmentRepository
) {

    companion object{
        private val logger = LoggerFactory.getLogger(ProjectBl::class.java)
    }

    fun approveProposal(proposalId: Long) {
        logger.info("Approving proposal: $proposalId")
        val proposal = proposalRepository.findById(proposalId).get()
        val enrollment = enrollmentRepository.findByProposalId(proposal)
        val assignation = assignationRepository.findByStudentId(proposal.person!!)
        logger.info("Assignation: ${assignation.assignationId}")
        enrollment.proposalStatus = "Aprobado"
        enrollmentRepository.save(enrollment)

        val project = Project(
            name = proposal.description!!,
            description = proposal.description!!,
            assignationId = assignation,
        )
        logger.info("Saving project: ${project.name}")
        projectRepository.save(project)

    }

    fun rejectProposal(proposalId: Long) {
        logger.info("Rejecting proposal: $proposalId")
        val proposal = proposalRepository.findById(proposalId).get()
        val enrollment = enrollmentRepository.findByProposalId(proposal)

        proposal.status = false
        proposalRepository.save(proposal)

        enrollment.proposalStatus = "Rechazado"
        enrollmentRepository.save(enrollment)
    }

}