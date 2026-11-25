package com.example.hospitalmanagement.model

//la clase enfermero que usan todas las pantallas
//no conectamos con el backend todavia, los datos estan en data/MockData.kt
data class Nurse(
    val id: Int,
    val name: String,
    val user: String,
    val password: String,
    val specialty: String,
    val shift: String,
    val phone: String
)
