package com.example.hospitalmanagement.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.data.listaEnfermeros
import com.example.hospitalmanagement.model.Nurse

@Composable
fun ListNursesScreen(
    onNavigateBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // mutableState modificado con un click: id del enfermero desplegado
    var seleccionado by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.enf_background))
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(colorResource(R.color.enf_primary), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(R.string.listar_desc_logo),
                tint = colorResource(R.color.enf_white),
                modifier = Modifier.size(36.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.listar_titulo),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.enf_title)
        )
        Text(
            text = stringResource(R.string.listar_subtitulo),
            fontSize = 15.sp,
            color = colorResource(R.color.enf_subtitle),
            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listaEnfermeros) { enfermero ->
                EnfermeroItem(
                    enfermero = enfermero,
                    expandido = seleccionado == enfermero.id,
                    onClick = {
                        seleccionado = if (seleccionado == enfermero.id) null else enfermero.id
                    }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        TextButton(onClick = onNavigateBack) {
            Text(
                text = stringResource(R.string.listar_volver),
                color = colorResource(R.color.enf_primary),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun EnfermeroItem(
    enfermero: Nurse,
    expandido: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(R.color.enf_tile), RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(colorResource(R.color.enf_white), RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = stringResource(R.string.listar_desc_enfermero),
                    tint = colorResource(R.color.enf_primary)
                )
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    text = enfermero.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.enf_title)
                )
                Text(
                    text = enfermero.specialty,
                    fontSize = 14.sp,
                    color = colorResource(R.color.enf_subtitle)
                )
            }
        }

        if (expandido) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.listar_etiqueta_id, enfermero.id.toString()),
                color = colorResource(R.color.enf_title)
            )
            Text(
                text = stringResource(R.string.listar_etiqueta_turno, enfermero.shift),
                color = colorResource(R.color.enf_title)
            )
            Text(
                text = stringResource(R.string.listar_etiqueta_telefono, enfermero.phone),
                color = colorResource(R.color.enf_title)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListNursesScreenPreview() {
    ListNursesScreen()
}
