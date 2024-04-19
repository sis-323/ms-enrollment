package com.enrollment.msenrollment.bl

import com.enrollment.msenrollment.dao.*
import com.enrollment.msenrollment.entity.Enrollment
import com.enrollment.msenrollment.entity.File
import com.enrollment.msenrollment.entity.Proposal
import com.enrollment.msenrollment.entity.StudentRequirement
import com.enrollment.msenrollment.service.EmailService
import com.enrollment.msenrollment.service.FileService
import com.files.msfiles.dto.FileDto
import com.files.msfiles.dto.ProposalDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.Date

@Service
class EnrollmentBl constructor(
        private var enrollmentRepository: EnrollmentRepository,
        private var proposalRepository: ProposalRepository,
        private var personRepository: PersonRepository,
        private var fileRepository: FileRepository,
        private var requirementRepository: StudentRequirementRepository,
        private var emailService: EmailService
){

    companion object{
        val logger: Logger = LoggerFactory.getLogger(EnrollmentBl::class.java)
    }

    fun saveEnrollment(file: FileDto, proposalDto: ProposalDto, files: List<FileDto>) {
        val personId = personRepository.findByIdKc(proposalDto.personKcUuid)
        val fileEntity = fileRepository.findById(file.fileId!!).get()

        val proposalEntity = Proposal(
                description = proposalDto.title,
                person = personId,
                status = true,
                fileId = fileEntity
        )

        val proposal =  proposalRepository.save(proposalEntity)
        val enrollment = Enrollment(
                studentId = personId,
                proposalId = proposal,
                enrollmentDate = Date(),
                semester = "2024-1"
        )
        val enrollmentAux = enrollmentRepository.save(enrollment)

        for (req in files){
            logger.info("Requisito: ${req.fileName}")
            val requirement = StudentRequirement(
                    enrollmentId = enrollmentAux,
                    fileId = fileRepository.findById(req.fileId!!).get(),
                    personId = personId,
                    requirementName = "Requisito")

            requirementRepository.save(requirement)
        }

        sendEmail("proposal", proposalDto.personKcUuid)




    }

    fun sendEmail(type: String, userKcUUid: String){
        emailService.sendEmail(userKcUUid, type)
    }




}