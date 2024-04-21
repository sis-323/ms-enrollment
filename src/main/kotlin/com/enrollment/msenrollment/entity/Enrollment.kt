package com.enrollment.msenrollment.entity

import com.enrollment.msenrollment.entity.Person
import com.enrollment.msenrollment.entity.Proposal
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "inscripcion")
class Enrollment(studentId: Person, proposalId: Proposal, @Column(name = "fecha_propuesta")
var enrollmentDate: Date, @Column(name = "semestre") var semester: String, @Column(name = "estado_propuest")
var proposalStatus: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscripcion")
    var enrollmentId: Long? = null

    @ManyToOne
    @JoinColumn(name = "id_persona")
    var studentId: Person? = studentId

    @ManyToOne
    @JoinColumn(name = "id_propuesta")
    var proposalId: Proposal? = proposalId

    @Column(name = "estado")
    var status: Boolean = true

}