package com.enrollment.msenrollment.bl

import com.enrollment.msenrollment.dao.*
import com.enrollment.msenrollment.dto.*
import com.enrollment.msenrollment.entity.Observation
import com.enrollment.msenrollment.entity.Project

import com.enrollment.msenrollment.entity.SearchProject

import com.enrollment.msenrollment.exception.StudentNotAssignedException

import com.enrollment.msenrollment.service.FileService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProjectBl(
    @Autowired private val proposalRepository: ProposalRepository,
    @Autowired private val assignationRepository: AssignationRepository,
    @Autowired private val projectRepository: ProjectRepository,
    @Autowired private val enrollmentRepository: EnrollmentRepository,
    private val fileService: FileService,
    @Autowired private val fileRepository: FileRepository,
    @Autowired private val modalityRepository: ModalityRepository,
    @Autowired private val requirementRepository: RequirementRepository,
    @Autowired private val observationRepository: ObservationRepository,
    @Autowired private val searchProjectRepository : SearchProjectRepository
) {

    companion object{
        private val logger = LoggerFactory.getLogger(ProjectBl::class.java)
    }

    fun approveProposal(proposalId: Long) {
        logger.info("Starting bl to approve proposal: $proposalId")
        val proposal = proposalRepository.findById(proposalId).get()
        val enrollment = enrollmentRepository.findByProposalId(proposal)
        val assignationExists = assignationRepository.existsByStudentId(proposal.person!!)
        if (!assignationExists) {
            logger.error("Student not assigned to any relator or tutor")
            throw StudentNotAssignedException("El estudiante no está asignado a ningún relator ni tutor")
        }

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

    fun reviewProposal(studentKcId: String, proposalId: Long, observationDto: ObservationDto) {
        logger.info("Observing proposal: $proposalId for student: $studentKcId")
        val proposal = proposalRepository.findByPersonIdKcAndProposalId(studentKcId, proposalId)
        val enrollment = enrollmentRepository.findByProposalId(proposal)
        val observation = Observation(
            description = observationDto.description,
            date = Date(),
        )
        val savedObservation = observationRepository.save(observation)
        enrollment.observation = savedObservation
        enrollment.proposalStatus = "Observado"
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

    fun findProposalByStudentKcId(studentKcId: String): List<ProposalOutDto> {
        val proposal = proposalRepository.findAllByPersonIdKc(studentKcId)
        return proposal.map {
            val enrollment = enrollmentRepository.findByProposalId(it)

            ProposalOutDto(
                proposalId = it.proposalId!!,
                title = it.description!!,
                uploadedBy = it.person!!.name,
                proposalStatus = enrollment.proposalStatus,
                fileUrl = fileService.getFileUrl(
                    fileRepository.findById(it.fileId!!.fileId!!).get().fileName!!,
                ).body!!.data!!,
                uploadedDate = enrollment.enrollmentDate.toString(),
                studentKcId = it.person!!.idKc
            )
        }

    }

    fun findProposalDetailByStudentKcId(studentKcId: String, proposalId: Long): ProposalDetailDto {
        val proposal = proposalRepository.findByPersonIdKcAndProposalId(studentKcId, proposalId)
        val enrollment = enrollmentRepository.findByProposalId(proposal)
        val user = proposal.person ?: throw IllegalStateException("Person associated with proposal is null")

        val requirements = requirementRepository.findRequirementsByPerson(user).map { requirement ->
            RequirementDto(
                requirementName = requirement.requirementName ?:
                throw IllegalStateException("Requirement name is null"),
                requirementLink = fileService.getFileUrl(
                    fileRepository.findById(requirement.file?.fileId ?:
                    throw IllegalStateException("File ID is null")).get().fileName!!
                ).body!!.data!!
            )
        }

        val proposalFile = fileService.getFileUrl(
            fileRepository.findById(proposal.fileId?.fileId ?:
            throw IllegalStateException("Proposal file ID is null")).get().fileName!!
        ).body!!.data!!

        return ProposalDetailDto(
            proposalId = proposal.proposalId!!,
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

    fun findFinalProjects(): List<SearchProjectResponseDto> {
        val projects = projectRepository.findAll()
        return projects.map {
            SearchProjectResponseDto(
                idProyectoFinal = it.projectId!!,
                titulo = it.name,
//                autor = it.assignationId!!.studentId!!.person!!.name + " " + it.assignationId!!.studentId!!.person!!.lastName,
                autor = "MOCK AUTHOR, CHANGE ms-enrollment/ProjectBl/167",
                estado = it.status,
                fecha = it.year,
//                tipo = it.assignationId!!.studentId!!.person!!.modality!!.modality
                tipo = "MOCK MODALITY, CHANGE ms-enrollment/ProjectBl/168"
            )
        }
    }

    fun searchProjects(term: String): List<SearchProjectResponseDto> {

        logger.info("Searching projects with term: $term")

        val queriedProjects = searchProjectRepository.searchProjects(term)
        // map to SearchProjectResponseDto
        var searchProjectResponseDto : List<SearchProjectResponseDto> = emptyList()
        for (project in queriedProjects) {
            // get author
//            val author = proposalRepository.findById(project.idProyectoFinal!!).get().person!!
//            val modality = author.modality!!.modality
            searchProjectResponseDto += SearchProjectResponseDto(
                idProyectoFinal = project.idProyectoFinal!!,
                titulo = project.titulo,
                autor  = "MOCK AUTHOR, CHANGE ms-enrollment/ProjectBl/186",
//                autor = author.name + " " + author.lastName + " " + author.motherLastName,
                estado = project.estado,
                fecha = project.year,
                tipo = "MOCK MODALITY, CHANGE ms-enrollment/ProjectBl/187"
//                tipo = modality
            )
        }
        return searchProjectResponseDto;
    }


    fun findPendingProjects(): List<SearchProjectResponseDto> {
        val projects = projectRepository.findAllByStatusFalse()
        return projects.map {
            SearchProjectResponseDto(
                idProyectoFinal = it.projectId!!,
                titulo = it.name,
//                autor = it.assignationId!!.studentId!!.person!!.name + " " + it.assignationId!!.studentId!!.person!!.lastName,
                autor = "MOCK AUTHOR, CHANGE ms-enrollment/ProjectBl/206",
                estado = it.status,
                fecha = it.year,
//                tipo = it.assignationId!!.studentId!!.person!!.modality!!.modality
                tipo = "MOCK MODALITY, CHANGE ms-enrollment/ProjectBl/207"
            )
        }
    }
}