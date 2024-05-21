package com.enrollment.msenrollment.entity

import jakarta.persistence.*

@Entity
@Table(name = "entregable_estudiante")
class StudentDeliverable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entregable")
    val studentDeliverableId: Long? = null

    @ManyToOne
    @JoinColumn(name = "id_archivo_entregable")
    var file: File? = null

    @ManyToOne
    @JoinColumn(name = "id_cronograma")
    var deliverable: Deliverable? = null

    @ManyToOne
    @JoinColumn(name = "id_asignacion")
    var assignation: Assignation? = null


    constructor(file: File?, deliverable: Deliverable?, assignation: Assignation?) {
        this.file = file
        this.deliverable = deliverable
        this.assignation = assignation
    }

}
