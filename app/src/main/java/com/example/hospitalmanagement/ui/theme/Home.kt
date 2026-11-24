package com.example.hospitalmanagement.ui.theme

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Colores del LoginScreen (si ya los tienes en tu Theme, usa esos)
private val PrimaryBlue = Color(0xFF0077B6)
private val DarkNavy = Color(0xFF1E3557)
private val BackgroundLight = Color(0xFFF0F4F8)
private val SubtitleGray = Color(0xFF5A6B80)

data class HomeOption(
    val id: String,
    val title: String,
    val icon: ImageVector
)

val homeOptions = listOf(
    HomeOption("login", "Iniciar\nsesión", Icons.Default.AccountCircle),
    HomeOption("listar_enfermeros", "Listar\nenfermeros", Icons.AutoMirrored.Filled.List),
    HomeOption("buscar_enfermeras", "Buscar\nenfermeras", Icons.Default.Search),
)

@Composable
fun HomeScreen(
    onProfileClick: () -> Unit = {},
    onOptionClick: (HomeOption) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        HomeTopBar(onProfileClick = onProfileClick)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 28.dp)
        ) {
            Text(
                text = "Bienvenido a tu espacio de trabajo",
                fontSize = 16.sp,
                color = SubtitleGray
            )

            Spacer(Modifier.height(32.dp))

            // Cuadrícula 3x3
            homeOptions.chunked(3).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    row.forEach { option ->
                        HomeTile(
                            option = option,
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
            .background(PrimaryBlue)
            .statusBarsPadding()
            .padding(horizontal = 8.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Enfermería",
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        IconButton(onClick = onProfileClick) {
            Icon(
                Icons.Default.AccountCircle,
                contentDescription = "Perfil",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
private fun HomeTile(
    option: HomeOption,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(PrimaryBlue.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = option.icon,
                contentDescription = null,
                tint = PrimaryBlue,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = option.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = DarkNavy,
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