package com.example.hospitalmanagement.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.hospitalmanagement.data.listaEnfermeros
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hospitalmanagement.ui.HomeScreen
import com.example.hospitalmanagement.ui.ListNursesScreen
import com.example.hospitalmanagement.ui.LoginScreen
import com.example.hospitalmanagement.ui.SearchScreen

//las rutas de cada pantalla
object Rutas {
    const val HOME = "home"
    const val LOGIN = "login"
    const val LISTAR = "listar"
    const val BUSCAR = "buscar"
}

//Home <-> Login, Home <-> Listar, Home <-> Buscar
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    //id del enfermero que ha hecho login (null = nadie)
    var idLogueado by rememberSaveable { mutableStateOf<Int?>(null) }
    val enfermeroLogueado = listaEnfermeros.find { it.id == idLogueado }

    //volver siempre a la Home
    val volverHome: () -> Unit = { navController.popBackStack(Rutas.HOME, inclusive = false) }

    NavHost(navController = navController, startDestination = Rutas.HOME) {

        composable(Rutas.HOME) {
            HomeScreen(
                enfermeroLogueado = enfermeroLogueado?.name,
                onLogout = { idLogueado = null },
                onOptionClick = { option ->
                    when (option.id) {
                        //solo se puede ir al login si todavia no hay nadie logueado
                        "login" -> if (enfermeroLogueado == null) navController.navigate(Rutas.LOGIN)
                        "listar_enfermeros" -> navController.navigate(Rutas.LISTAR)
                        "buscar_enfermeras" -> navController.navigate(Rutas.BUSCAR)
                    }
                }
            )
        }

        composable(Rutas.LOGIN) {
            LoginScreen(
                onNavigateBack = volverHome,
                onLoginSuccess = { enfermero ->
                    idLogueado = enfermero.id
                    volverHome()
                }
            )
        }

        composable(Rutas.LISTAR) {
            ListNursesScreen(onNavigateBack = volverHome)
        }

        composable(Rutas.BUSCAR) {
            SearchScreen(onNavigateBack = volverHome)
        }
    }
}
