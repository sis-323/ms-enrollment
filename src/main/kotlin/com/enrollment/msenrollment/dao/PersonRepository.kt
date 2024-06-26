package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository: JpaRepository<Person, Long>{
    fun findByIdKc(kcUuid: String): Person

    @Query("SELECT p FROM Person p WHERE p.group = 'professors'")
    fun findProfessor(): Person
}