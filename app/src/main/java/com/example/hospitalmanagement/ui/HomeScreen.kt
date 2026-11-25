package com.example.hospitalmanagement.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.hospitalmanagement.R

data class HomeOption(
    val id: String,
    @StringRes val title: Int,
    val icon: ImageVector
)

val homeOptions = listOf(
    HomeOption("login", R.string.home_option_login, Icons.Default.AccountCircle),
    HomeOption("listar_enfermeros", R.string.home_option_list, Icons.AutoMirrored.Filled.List),
    HomeOption("buscar_enfermeras", R.string.home_option_search, Icons.Default.Search),
)

@Composable
fun HomeScreen(
    //nombre del enfermero que ha hecho login (null = nadie)
    enfermeroLogueado: String? = null,
    onProfileClick: () -> Unit = {},
    //se llama al pulsar "Cerrar sesión" en el aviso del perfil
    onLogout: () -> Unit = {},
    onOptionClick: (HomeOption) -> Unit = {}
) {
    //mutableState: al pulsar el icono de perfil se muestra u oculta la info
    var mostrarPerfil by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.medical_background))
    ) {
        HomeTopBar(onProfileClick = {
            mostrarPerfil = !mostrarPerfil
            onProfileClick()
        })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 28.dp)
        ) {
            //si hay alguien logueado, se le saluda
            if (enfermeroLogueado != null) {
                Text(
                    text = stringResource(R.string.home_greeting, enfermeroLogueado),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.text_dark)
                )
                Spacer(Modifier.height(4.dp))
            }
            Text(
                text = stringResource(R.string.home_welcome),
                fontSize = 16.sp,
                color = colorResource(R.color.enf_subtitle)
            )

            if (mostrarPerfil) {
                Spacer(Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(colorResource(R.color.medical_card))
                        .padding(12.dp)
                ) {
                    Text(
                        text = if (enfermeroLogueado != null) {
                            stringResource(R.string.home_profile_logged, enfermeroLogueado)
                        } else {
                            stringResource(R.string.home_profile_info)
                        },
                        fontSize = 14.sp,
                        color = colorResource(R.color.text_dark)
                    )

                    //solo se puede cerrar sesion si hay alguien logueado
                    if (enfermeroLogueado != null) {
                        TextButton(
                            onClick = {
                                mostrarPerfil = false
                                onLogout()
                            },
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.home_logout),
                                color = colorResource(R.color.medical_error),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            // Botones de la Home, 3 por fila
            homeOptions.chunked(3).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    row.forEach { option ->
                        //una vez logueado no se puede volver a hacer login
                        val yaLogueado = option.id == "login" && enfermeroLogueado != null
                        HomeTile(
                            option = option,
                            titulo = if (yaLogueado) R.string.home_option_logged else option.title,
                            activo = !yaLogueado,
                            onClick = { onOptionClick(option) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // Rellena huecos si la última fila no está completa
                    repeat(3 - row.size) { Spacer(Modifier.weight(1f)) }
                }
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun HomeTopBar(
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.medical_primary))
            .statusBarsPadding()
            .padding(horizontal = 8.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.app_name_enfermeria),
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
            color = colorResource(R.color.white),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        IconButton(onClick = onProfileClick) {
            Icon(
                Icons.Default.AccountCircle,
                contentDescription = stringResource(R.string.home_profile_desc),
                tint = colorResource(R.color.white),
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
private fun HomeTile(
    option: HomeOption,
    @StringRes titulo: Int,
    activo: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(enabled = activo, onClick = onClick)
            .alpha(if (activo) 1f else 0.5f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(colorResource(R.color.medical_card)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = option.icon,
                contentDescription = null,
                tint = colorResource(R.color.medical_primary),
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(titulo),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = colorResource(R.color.text_dark),
            textAlign = TextAlign.Center,
            lineHeight = 17.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}

//como se ve la Home despues de hacer login
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenLogueadoPreview() {
    MaterialTheme {
        HomeScreen(enfermeroLogueado = "Ana García")
    }
}