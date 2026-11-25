package com.example.hospitalmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.hospitalmanagement.navigation.AppNavigation
import com.example.hospitalmanagement.ui.theme.HospitalManagementTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HospitalManagementTheme {
                AppNavigation()
            }
        }
    }
}
