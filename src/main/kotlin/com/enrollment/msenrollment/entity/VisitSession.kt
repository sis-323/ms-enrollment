package com.enrollment.msenrollment.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "sesiones_visita")
class VisitSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesision")
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "id_asignacion")
    var assignation: Assignation? = null

    @Column(name = "fecha")
    var date: Date? = null

    @Column(name = "observacion")
    var observation: String? = null

    @Column(name = "estudiante_asistio")
    var studentAssisted: Boolean? = null

    @Column(name = "estado")
    var status: Boolean? = true

    constructor(
        assignation: Assignation?,
        date: Date?,
        observation: String?,
        studentAssisted: Boolean?,
        status: Boolean?
    ) {

        this.assignation = assignation
        this.date = date
        this.observation = observation
        this.studentAssisted = studentAssisted
        this.status = status
    }

}