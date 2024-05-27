package com.enrollment.msenrollment.bl

import com.enrollment.msenrollment.dao.*
import com.enrollment.msenrollment.dto.DeliverableDto
import com.enrollment.msenrollment.entity.Deliverable
import com.enrollment.msenrollment.entity.DeliverableFile
import com.enrollment.msenrollment.entity.StudentDeliverable
import com.enrollment.msenrollment.service.FileService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class DeliverableBl (
    @Autowired private val deliverableRepository: DeliverableRepository,
    @Autowired private val deliverableFileRepository: DeliverableFileRepository,
    @Autowired private val fileService: FileService,
    @Autowired private val fileRepository: FileRepository,
    @Autowired private val assignationRepository: AssignationRepository,
    @Autowired private val projectRepository: ProjectRepository
) {
    companion object{
        val logger: Logger = LoggerFactory.getLogger(DeliverableBl::class.java)
    }

    fun saveDeliverable(deliverableDto: DeliverableDto, fileId: Long) {
        val finalProjects = projectRepository.findAll()
        logger.info("Projects: $finalProjects")
        val file = fileRepository.findByFileId(fileId)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date: Date = dateFormat.parse(deliverableDto.dueDate) ?: Date()

        val deliverable = Deliverable(
            title = deliverableDto.title,
            dueDate = date,
            description = deliverableDto.description,
            status = true
        )

        val savedDeliverable = deliverableRepository.save(deliverable)

        for (project in finalProjects) {
            val deliverableFile = DeliverableFile(
                deliverableId = savedDeliverable,
                fileId = file!!,
                finalProjectId = project

            )
            logger.info("DeliverableFile: ${deliverableFile.toString()}")
            deliverableFileRepository.save(deliverableFile)
        }
    }

    fun findDeliverables(): List<DeliverableDto>{

        val deliverables = deliverableRepository.findAll()
        val deliverableDtos = mutableListOf<DeliverableDto>()
        deliverables.forEach {
            deliverableDtos.add(DeliverableDto(
                deliverableId = it.deliverableId,
                title = it.title!!,
                dueDate = it.dueDate.toString(),
                description = it.description!!
            ))
        }
        return deliverableDtos
    }

//    fun saveStudentDeliverable(MultipartFile file, Long deliverableId, String studentKcId) {
//        val deliverable = deliverableRepository.findByDeliverableId(deliverableId)
//        val assignation = assignationRepository.findByStudentIdIdKc(studentKcId)
//        val fileUrl = fileService.uploadFile(file)
//
//        val studentDeliverable = StudentDeliverable(
//            deliverable = deliverable,
//            assignation = assignation,
//            file =
//        )
//
//    }

}