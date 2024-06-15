package com.enrollment.msenrollment.entity

import jakarta.persistence.*
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "carta_cumplimiento")
class CompletionLetter(
    @ManyToOne
    @JoinColumn(name = "id_archivo") var fileId: File?,
    @ManyToOne
    @JoinColumn(name = "id_proyecto_final") var finalProjectId: Project?,
    @Column(name = "fecha") var date: Date?,
    @Column(name = "hora") var time: Timestamp?,
    @Column(name = "aprobada_por_tutor") var approvedByTutor: Boolean,
    @Column(name = "aprobado_por_relator") var approvedByRelator: Boolean
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carta")
    var completionLetterId: Long? = null


}