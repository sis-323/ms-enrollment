package com.enrollment.msenrollment.dto

data class SearchProjectResponseDto(
    val idProyectoFinal: Long,
    val titulo: String,
    val autor: String,
    val estado : Boolean,
    val fecha: String,
    val tipo: String? = null,
)
