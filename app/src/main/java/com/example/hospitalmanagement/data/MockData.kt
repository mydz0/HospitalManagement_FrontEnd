package com.example.hospitalmanagement.data

import com.example.hospitalmanagement.model.Nurse

//datos de prueba: la misma lista para login, listar y buscar
//hay 3 que se llaman Ana para que al buscar "Ana" salgan 3 resultados
val listaEnfermeros = listOf(
    Nurse(1, "Ana García", "agarcia", "1234", "Cuidados intensivos", "Mañana", "600 111 222"),
    Nurse(2, "Carlos Pérez", "cperez", "1234", "Urgencias", "Tarde", "600 333 444"),
    Nurse(3, "Ana López", "alopez", "1234", "Pediatría", "Noche", "600 555 666"),
    Nurse(4, "Laura Martín", "lmartin", "1234", "Quirófano", "Mañana", "600 777 888"),
    Nurse(5, "Ana Torres", "atorres", "1234", "Cardiología", "Tarde", "600 999 000"),
    Nurse(6, "Jordi Puig", "jpuig", "1234", "Traumatología", "Noche", "600 123 456")
)

//login de prueba: se puede entrar con el id (1), el usuario (agarcia) o el nombre (Ana García)
fun buscarEnfermeroLogin(identificador: String, password: String): Nurse? {
    val texto = identificador.trim()
    return listaEnfermeros.find {
        (it.id.toString() == texto ||
            it.user.equals(texto, ignoreCase = true) ||
            it.name.equals(texto, ignoreCase = true)) &&
            it.password == password
    }
}
