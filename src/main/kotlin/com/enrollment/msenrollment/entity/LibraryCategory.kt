package com.enrollment.msenrollment.entity

import jakarta.persistence.*

@Entity
@Table(name = "materias_biblioteca")
class LibraryCategory(@Column(name = "materia") var category: String?) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_materia_biblioteca")
    var id: Long? = null

    @Column(name = "estado")
    var status: Boolean? = null

    @ManyToMany(mappedBy = "categories")
    var approvedProjects: Set<ApprovedProject>? = setOf()

}