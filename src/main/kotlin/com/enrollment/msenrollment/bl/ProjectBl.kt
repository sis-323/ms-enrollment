package com.enrollment.msenrollment.bl

import com.enrollment.msenrollment.dao.*
import com.enrollment.msenrollment.dto.ModalityDto
import com.enrollment.msenrollment.dto.ProposalOutDto
import com.enrollment.msenrollment.entity.Project
import com.enrollment.msenrollment.service.FileService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProjectBl(
    @Autowired private val proposalRepository: ProposalRepository,
    @Autowired private val assignationRepository: AssignationRepository,
    @Autowired private val projectRepository: ProjectRepository,
    @Autowired private val enrollmentRepository: EnrollmentRepository,
    private val fileService: FileService,
    @Autowired private val fileRepository: FileRepository,
    @Autowired private val modalityRepository: ModalityRepository
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

    fun findProposals():List<ProposalOutDto>{
        val proposals = proposalRepository.findAll()
        val enrollments = enrollmentRepository.findAll()
        logger.info(enrollments.toString())
        return proposals.map {
            ProposalOutDto(
                proposalId = it.proposalId!!,
                title = it.description!!,
                uploadedBy = it.person!!.name!!,
                proposalStatus = enrollments.find { enrollment -> enrollment.proposalId == it }!!.proposalStatus!!,
                fileUrl = fileService.getFileUrl(
                    fileRepository.findById(it.fileId!!.fileId!!).get().fileName!!,
                ).body!!.data!!
            )
        }
    }

    fun findModalities(): List<ModalityDto> {
        val modalities = modalityRepository.findAll()
        return modalities.map {
            ModalityDto(
                it.idModality,
                it.modality
            )
        }
    }

}