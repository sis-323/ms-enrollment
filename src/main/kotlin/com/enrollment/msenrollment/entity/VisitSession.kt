package com.enrollment.msenrollment.entity

import jakarta.persistence.*
import java.sql.Time
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

    @Column(name = "hora")
    var hour: Date? = null

    @Column(name = "plataforma")
    var platform: String? = null

    @Column(name = "enlace")
    var link: String? = null

    @Column(name = "accion_realizada")
    var performedAction: String? = null


    constructor(
        assignation: Assignation,
        date: Date,
        observation: String?,
        studentAssisted: Boolean?,
        status: Boolean,
        hour: Date,
        platform: String,
        link: String,
        performedAction: String
    ) {
        this.assignation = assignation
        this.date = date
        this.observation = observation
        this.studentAssisted = studentAssisted
        this.status = status
        this.hour = hour
        this.platform = platform
        this.link = link
        this.performedAction = performedAction
    }

}