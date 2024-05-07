package com.enrollment.msenrollment.entity

import jakarta.persistence.*

@Entity
@Table(name = "requisitos_estudiante")
class Requirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_requisito_estudiante")
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "id_inscripcion")
    var enrollment: Enrollment? = null

    @OneToOne
    @JoinColumn(name = "id_archivo")
    var file: File? = null

    @ManyToOne
    @JoinColumn(name = "id_persona")
    var person: Person? = null

    @Column(name = "nombre_requisito")
    var requirementName: String? = null




}