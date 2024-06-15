package com.enrollment.msenrollment.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "proyectos_finales")
class Project(
    @Column(name = "titulo") var name: String,
    assignationId: Assignation
) {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto_final")
    var projectId: Long? = null

    @Column(name = "estado")
    var status: Boolean = true

    @Column(name = "year")
    var year: String = LocalDate.now().year.toString()


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_asignacion")
    var assignationId: Assignation? = assignationId

    @Column(name = "finalizado")
    var isFinished: Boolean = false
}