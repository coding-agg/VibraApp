package com.example.vibraapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.vibraapp.auth.AuthRepository
import com.example.vibraapp.auth.TokenManager
import com.example.vibraapp.navigation.VibraNavHost
import com.example.vibraapp.ui.theme.VibraAppTheme
import com.example.vibraapp.viewmodel.AuthViewModel
import com.example.vibraapp.viewmodel.AuthViewModelFactory
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = AuthRepository(applicationContext)
        val tokenManager = TokenManager(applicationContext)
        val factory = AuthViewModelFactory(repository, tokenManager)
        val authViewModel: AuthViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        setContent {
            VibraAppContent(authViewModel = authViewModel)
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VibraAppTheme {
        Greeting("Android")
    }
}