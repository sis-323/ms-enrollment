package com.enrollment.msenrollment.dao

import com.enrollment.msenrollment.entity.SearchProject
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface SearchProjectRepository : CrudRepository<SearchProject, Long>{
    @Query(value = "SELECT * FROM search_projects(:term)", nativeQuery = true)
    fun searchProjects(term: String): List<SearchProject>
}