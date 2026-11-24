package com.example.hospitalmanagement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    onNavigateBack: () -> Unit = {},
    onLoginSubmit: (identificador: String, password: String) -> Unit = { _, _ -> }
) {
    var identificador by remember { mutableStateOf("") } // Puede ser ID o nombre
    var password by remember { mutableStateOf("") }
    var mensajeEstado by remember { mutableStateOf("") }
    var hayError by remember { mutableStateOf(false) }

    val colorPrincipal = colorResource(id = R.color.medical_primary)
    val colorFondo = colorResource(id = R.color.medical_background)
    val colorError = colorResource(id = R.color.medical_error)
    val colorCorrecto = colorResource(id = R.color.medical_success)
    val colorTexto = colorResource(id = R.color.text_dark)

    val textoError = stringResource(id = R.string.error_empty_fields)
    val textoOk = stringResource(id = R.string.login_success_msg)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorFondo)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.LocalHospital,
            contentDescription = stringResource(id = R.string.logo_desc),
            tint = colorPrincipal,
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(id = R.string.login_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorTexto
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo para ID o nombre del enfermero
        OutlinedTextField(
            value = identificador,
            onValueChange = { identificador = it },
            label = { Text("ID o nombre de enfermero") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.password_label)) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        if (mensajeEstado.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = mensajeEstado,
                color = if (hayError) colorError else colorCorrecto,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val limpio = identificador.trim()
                if (limpio.isBlank() || password.isBlank()) {
                    hayError = true
                    mensajeEstado = textoError
                } else {
                    hayError = false
                    mensajeEstado = textoOk
                    onLoginSubmit(limpio, password)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorPrincipal),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.login_button))
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onNavigateBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.back_home_button),
                color = colorPrincipal
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}