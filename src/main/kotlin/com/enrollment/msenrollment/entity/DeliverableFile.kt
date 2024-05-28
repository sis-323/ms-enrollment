package com.enrollment.msenrollment.entity

import jakarta.persistence.*

@Entity
@Table(name="entregable_cronograma_archivo")
class DeliverableFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_entregable_archivo")
    var deliverableFileId: Long? = null

    @ManyToOne
    @JoinColumn(name="id_proyecto_final")
    var finalProjectId: Project? = null

    @ManyToOne
    @JoinColumn(name = "id_cronograma")
    var deliverableId: Deliverable? = null

    @ManyToOne
    @JoinColumn(name = "id_archivo")
    var fileId: File? = null

    @Column(name = "estado")
    var status: Boolean = true

    constructor(finalProjectId: Project, deliverableId: Deliverable, fileId: File) {
        this.finalProjectId = finalProjectId
        this.deliverableId = deliverableId
        this.fileId = fileId
    }

}