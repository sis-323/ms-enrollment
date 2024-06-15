package com.enrollment.msenrollment.entity

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "proyecto_aprobado")
class ApprovedProject(
    @ManyToOne
    @JoinColumn(name = "id_acta") var minuteId: DefenseMinute?,
    @Column(name = "fecha_aprobado") var approvedDate: Date?,
    @Column(name = "resumen") var summary: String?,
    @Column(name = "titulo_oficial") var officialTitle: String?,
    @Column(name = "autor") var author: String?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto_aprobado")
    var id: Long? = null

}