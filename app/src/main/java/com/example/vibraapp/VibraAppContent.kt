package com.example.vibraapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vibraapp.navigation.VibraNavHost
import com.example.vibraapp.ui.theme.VibraAppTheme
import com.example.vibraapp.viewmodel.AuthViewModel

@Composable
fun VibraAppContent(authViewModel: AuthViewModel) {
    VibraAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            VibraNavHost(authViewModel = authViewModel)
        }
    }
}
