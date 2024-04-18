package com.enrollment.msenrollment.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "requisitos_estudiante")
class StudentRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_requisito_estudiante")
    var requirementId: Long? = null

    @ManyToOne
    @JoinColumn(name = "id_inscripcion")
    var enrollmentId: Enrollment? = null

    @ManyToOne
    @JoinColumn(name = "id_archivo")
    var fileId: File? = null

    @ManyToOne
    @JoinColumn(name = "id_persona")
    var personId: Person? = null

    @Column(name = "nombre_requisito")
    var requirementName: String = ""


    constructor(
            enrollmentId: Enrollment,
            fileId: File,
            personId: Person,
            requirementName: String
    ) {
        this.enrollmentId = enrollmentId
        this.fileId = fileId
        this.personId = personId
        this.requirementName = requirementName
    }

}