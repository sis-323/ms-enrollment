package com.enrollment.msenrollment.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "entregable_estudiante")
class StudentDeliverable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entregable")
    val studentDeliverableId: Long? = null

    @ManyToOne
    @JoinColumn(name = "id_archivo_entregable")
    var file: StudentDeliverableFile? = null

    @ManyToOne
    @JoinColumn(name = "id_cronograma")
    var deliverable: Deliverable? = null

    @ManyToOne
    @JoinColumn(name = "id_asignacion")
    var assignation: Assignation? = null

    @Column(name = "estado")
    var status: String? = null

    @Column(name = "fecha")
    var date: Date = Date()


    constructor(file: StudentDeliverableFile?, deliverable: Deliverable?, assignation: Assignation?,
        status: String? = null)
    {
        this.file = file
        this.deliverable = deliverable
        this.assignation = assignation
        this.status = status
    }

}
