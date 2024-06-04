package com.enrollment.msenrollment.entity

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "observacion")
class Observation() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_observacion")
    var observationId: Long? = null
    @Column(name = "observaci") var description: String? = null
    @Column(name = "fecha") var date: Date = Date()
    @Column(name = "subido_por") var uploadedBy: String? = null

    constructor(
        description: String?,
        date: Date?,
        uploadedBy: String?
    ) : this() {
        this.description = description
        this.date = date!!
        this.uploadedBy = uploadedBy
    }


}