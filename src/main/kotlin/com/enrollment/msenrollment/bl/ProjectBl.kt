package com.enrollment.msenrollment.bl

import com.enrollment.msenrollment.dao.*
import com.enrollment.msenrollment.dto.ModalityDto
import com.enrollment.msenrollment.dto.ProposalDetailDto
import com.enrollment.msenrollment.dto.ProposalOutDto
import com.enrollment.msenrollment.dto.RequirementDto
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
    @Autowired private val modalityRepository: ModalityRepository,
    @Autowired private val requirementRepository: RequirementRepository
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
                uploadedBy = it.person!!.name + " " + it.person!!.lastName,
                proposalStatus = enrollments.find { enrollment -> enrollment.proposalId == it }!!.proposalStatus!!,
                fileUrl = fileService.getFileUrl(
                    fileRepository.findById(it.fileId!!.fileId!!).get().fileName!!,
                ).body!!.data!!,
                uploadedDate = enrollments.find
                { enrollment -> enrollment.proposalId == it }!!.enrollmentDate.toString(),
                studentKcId = it.person!!.idKc
            )
        }
    }

    fun findProposalByStudentKcId(studentKcId: String): ProposalOutDto {
        val proposal = proposalRepository.findByPersonIdKc(studentKcId)
        val enrollment = enrollmentRepository.findByProposalId(proposal)
        return ProposalOutDto(
            proposalId = proposal.proposalId!!,
            title = proposal.description!!,
            uploadedBy = proposal.person!!.name,
            proposalStatus = enrollment.proposalStatus,
            fileUrl = fileService.getFileUrl(
                fileRepository.findById(proposal.fileId!!.fileId!!).get().fileName!!,
            ).body!!.data!!,
            uploadedDate = enrollment.enrollmentDate.toString(),
            studentKcId = proposal.person!!.idKc

        )
    }

    fun findProposalDetailByStudentKcId(studentKcId: String): ProposalDetailDto {
        val proposal = proposalRepository.findByPersonIdKc(studentKcId)
        val enrollment = enrollmentRepository.findByProposalId(proposal)
        val user = proposal.person ?: throw IllegalStateException("Person associated with proposal is null")

        val requirements = requirementRepository.findRequirementsByPerson(user).map { requirement ->
            RequirementDto(
                requirementName = requirement.requirementName ?: throw IllegalStateException("Requirement name is null"),
                requirementLink = fileService.getFileUrl(
                    fileRepository.findById(requirement.file?.fileId ?: throw IllegalStateException("File ID is null")).get().fileName!!
                ).body!!.data!!
            )
        }

        val proposalFile = fileService.getFileUrl(
            fileRepository.findById(proposal.fileId?.fileId ?: throw IllegalStateException("Proposal file ID is null")).get().fileName!!
        ).body!!.data!!

        return ProposalDetailDto(
            email = user.email,
            fullName = "${user.name} ${user.lastName}",
            requirements = requirements,
            proposalFile = proposalFile,
            proposalStatus = enrollment.proposalStatus,
            proposalFileName = proposal.description!!
        )
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