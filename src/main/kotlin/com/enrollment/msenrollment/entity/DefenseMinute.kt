package com.enrollment.msenrollment.entity

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "acta_defensa")
class DefenseMinute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acta")
    var id: Int? = null

    @ManyToOne
    @JoinColumn(name = "id_archivo")
    var file: File? = null

    @ManyToOne
    @JoinColumn(name = "id_proyecto_final")
    var finalProject: Project? = null

    @ManyToOne
    @JoinColumn(name = "id_carta")
    var letter: CompletionLetter? = null

    @Column(name = "fecha")
    var date: Date? = null

    @Column(name = "nota")
    var note: Int? = null

    @Column(name = "estado")
    var status: String? = null

    @Column(name = "horario")
    var schedule: String? = null



}