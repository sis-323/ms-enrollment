package com.enrollment.msenrollment.bl

import com.enrollment.msenrollment.dao.CompletionLetterRepository
import com.enrollment.msenrollment.dao.FileRepository
import com.enrollment.msenrollment.dao.PersonRepository
import com.enrollment.msenrollment.dao.ProjectRepository
import com.enrollment.msenrollment.dto.CompletionLetterDto
import com.enrollment.msenrollment.entity.CompletionLetter
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CompletionLetterBl (
    @Autowired val completionLetterRepository: CompletionLetterRepository,
    @Autowired val fileRepository: FileRepository,
    @Autowired val projectRepository: ProjectRepository,
    @Autowired val personRepository: PersonRepository
) {
    companion object {
        val logger: Logger = org.slf4j.LoggerFactory.getLogger(CompletionLetterBl::class.java)
    }

    fun saveCompletionLetter(completionLetterDto: CompletionLetterDto) {
        logger.info("Saving completion letter")
        val file = fileRepository.findById(completionLetterDto.fileId).get()
        val project = projectRepository.findById(completionLetterDto.finalProjectId).get()
        val completionLetter = CompletionLetter(
            fileId = file,
            finalProjectId = project,
            date = completionLetterDto.date,
            time = completionLetterDto.time,
            approvedByTutor = completionLetterDto.approvedByTutor,
            approvedByRelator = completionLetterDto.approvedByRelator
        )
        completionLetterRepository.save(completionLetter)
        logger.info("Completion letter saved")

    }

    fun approveCompletionLetter(projectId: Long, kcId: String) {
        logger.info("Approving completion letter")
        val person = personRepository.findByIdKc(kcId)
        if (person.group == "tutors") {
            logger.info("Tutor approving completion letter")
            val completionLetter = completionLetterRepository.findByFinalProjectIdProjectId(projectId)
            completionLetter.approvedByTutor = true
            completionLetterRepository.save(completionLetter)
        }
        if (person.group == "relators") {
            logger.info("Relator approving completion letter")
            val completionLetter = completionLetterRepository.findByFinalProjectIdProjectId(projectId)
            completionLetter.approvedByRelator = true
            completionLetterRepository.save(completionLetter)
        }
        logger.info("Completion letter approved")
    }

    fun validateCompletionLetter(projectId: Long): Boolean {
        logger.info("Validating completion letter")
        val completionLetterExists = completionLetterRepository.existsByFinalProjectIdProjectId(projectId)
        if (!completionLetterExists) {
            logger.info("Completion letter does not exist")
            return false
        } else {
            val completionLetter = completionLetterRepository.findByFinalProjectIdProjectId(projectId)
            if (completionLetter.approvedByTutor && completionLetter.approvedByRelator) {
                return true
            } else {
                logger.info("Completion letter not approved")
                return false
            }
        }
    }


}