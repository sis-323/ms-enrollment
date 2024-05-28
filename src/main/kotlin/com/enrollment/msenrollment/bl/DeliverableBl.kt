package com.enrollment.msenrollment.bl

import com.enrollment.msenrollment.dao.*
import com.enrollment.msenrollment.dto.DeliverableDto
import com.enrollment.msenrollment.entity.Deliverable
import com.enrollment.msenrollment.entity.DeliverableFile
import com.enrollment.msenrollment.entity.StudentDeliverable
import com.enrollment.msenrollment.entity.StudentDeliverableFile
import com.enrollment.msenrollment.service.FileService
import com.files.msfiles.dto.FileDto
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
    @Autowired private val projectRepository: ProjectRepository,
    private var studentDeliverableFileRepository: StudentDeliverableFileRepository,
    private var studentDeliverableRepository: StudentDeliverableRepository
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
                description = it.description!!,
            ))
        }
        return deliverableDtos
    }

    fun saveStudentDeliverable(file: FileDto, studentKcId: String, deliverableId: Long){
        val assignation = assignationRepository.findByStudentIdIdKc(studentKcId)
        val deliverable = deliverableRepository.findById(deliverableId).get()
        logger.info("Looking for file with id: ${file.fileId}")
        val studentFileDeliverable = StudentDeliverableFile(
            file = fileRepository.findById(file.fileId!!).get())
        studentDeliverableFileRepository.save(studentFileDeliverable)

        val studentDeliverable = StudentDeliverable(
            deliverable = deliverable,
            assignation = assignation,
            file = studentFileDeliverable,
            status = "Pendiente"
        )
        studentDeliverableRepository.save(studentDeliverable)


    }

    fun findDeliverablesByStudentKcId( projectId: Long): List<DeliverableDto> {
        val studentDeliverables = studentDeliverableRepository.findByProjectId(projectId)
        val deliverableDtos = mutableListOf<DeliverableDto>()
        studentDeliverables.forEach {
            deliverableDtos.add(DeliverableDto(
                deliverableId = it.deliverable?.deliverableId,
                title = it.deliverable?.title!!,
                dueDate = it.deliverable?.dueDate.toString(),
                description = it.deliverable?.description!!,
                fileUrl = fileService.getFileUrl(it.file?.file?.fileName!!).body?.data,
                status = it.status

            ))
        }
        return deliverableDtos
    }


    fun findPendingDeliverables(studentKcId: String) : List<DeliverableDto>{
        val deliverableDtos = mutableListOf<DeliverableDto>()
        val assignation = assignationRepository.findByStudentIdIdKc(studentKcId)
        val project = projectRepository.findByAssignationId(assignation)
        val deliverables = deliverableFileRepository.findAllByFinalProjectId(project)
        deliverables.forEach {
            deliverableDtos.add(DeliverableDto(
                deliverableId = it.deliverableId?.deliverableId,
                title = it.deliverableId?.title!!,
                dueDate = it.deliverableId?.dueDate.toString(),
                description = it.deliverableId?.description!!,
                fileUrl = fileService.getFileUrl(it.fileId?.fileName!!).body?.data

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