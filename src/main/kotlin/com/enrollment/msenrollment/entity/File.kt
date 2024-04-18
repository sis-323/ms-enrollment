package com.enrollment.msenrollment.entity

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "archivo")
class File(fileName: String, md5: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_archivo")
    var fileId: Long? = null

    @Column(name = "nombre_archivo")
    var fileName: String? = fileName

    @Column(name = "md5")
    var md5: String? = md5

    @Column(name = "fecha_subido")
    var uploadDate: Date? = null

    init {
        this.uploadDate = Date()
    }

}