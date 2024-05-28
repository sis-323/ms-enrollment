package com.enrollment.msenrollment.entity

import jakarta.persistence.*
import java.util.*
@Entity
@Table(name = "inscripcion")
class Enrollment(
    @ManyToOne
    @JoinColumn(name = "id_persona")
    var studentId: Person,

    @ManyToOne
    @JoinColumn(name = "id_propuesta")
    var proposalId: Proposal,

    @Column(name = "fecha_propuesta")
    var enrollmentDate: Date,

    @Column(name = "semestre")
    var semester: String,

    @Column(name = "estado_propuest")
    var proposalStatus: String,

    @ManyToOne
    @JoinColumn(name = "id_observacion")
    var observation: Observation? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscripcion")
    var enrollmentId: Long? = null

    @Column(name = "estado")
    var status: Boolean = true


}
