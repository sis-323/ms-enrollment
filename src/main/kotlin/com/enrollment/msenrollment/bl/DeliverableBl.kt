package com.enrollment.msenrollment.bl

import com.enrollment.msenrollment.dao.DeliverableRepository
import com.enrollment.msenrollment.dto.DeliverableDto
import com.enrollment.msenrollment.entity.Deliverable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class DeliverableBl (
    @Autowired private val deliverableRepository: DeliverableRepository
) {

    fun saveDeliverable(deliverableDto: DeliverableDto) {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date: Date = dateFormat.parse(deliverableDto.dueDate) ?: Date()

        val deliverable = Deliverable(
            title = deliverableDto.title,
            dueDate = date,
            description = deliverableDto.description,
            status = true
        )

        deliverableRepository.save(deliverable)
    }


}