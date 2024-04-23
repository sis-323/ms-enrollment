package com.enrollment.msenrollment.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "entregables_cronograma")
class Deliverable(
    @Column(name = "titulo") var title: String?,
    @Column(name = "fecha_limite") var dueDate: Date,
    @Column(name = "descripcion") var description: String?,
    @Column(name = "estado") var status: Boolean
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cronograma")
    var deliverableId: Long? = null

}