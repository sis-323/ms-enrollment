package com.enrollment.msenrollment.entity


import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "propuestas")
class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_propuesta")
    var proposalId: Long? = null

    @ManyToOne
    @JoinColumn(name = "id_archivo")
    var fileId: File? = null

    @Column(name = "titulo")
    var description: String? = null

    @Column(name = "estado")
    var status: Boolean = true

    @OneToOne
    @JoinColumn(name = "id_persona")
    var person: Person? = null

    constructor(fileId: File, description: String, person: Person,
                status: Boolean) {
        this.fileId = fileId
        this.description = description
        this.person = person
        this.status = status
    }




}