package com.enrollment.msenrollment.entity

import jakarta.persistence.*

@Entity
@Table(name = "archivo_entregable")
class StudentDeliverableFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_archivo_entregable")
    val studentDeliverableFileId: Long? = null

    @ManyToOne
    @JoinColumn(name = "id_archivo")
    var file: File? = null

    constructor(
        file: File?
    ) {
        this.file = file
    }

}