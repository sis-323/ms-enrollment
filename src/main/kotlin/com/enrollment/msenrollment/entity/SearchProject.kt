package com.enrollment.msenrollment.entity

import jakarta.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "proyectos_finales")
data class SearchProject(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto_final")
    val idProyectoFinal: Long? = null,

    @Column(name = "id_asignacion")
    val idAsignacion : Int,

    @Column(name = "titulo")
    val titulo: String,

    @Column(name = "resumen")
    val resumen: String,

    @Column(name = "year")
    val year: String,

    @Column(name = "materia")
    val materia: Int,

    @Column(name = "estado")
    val estado: Boolean,

    @Column(name = "finalizado")
    val finalizado: Boolean,

    @Transient
    val rank: Float? = null
)