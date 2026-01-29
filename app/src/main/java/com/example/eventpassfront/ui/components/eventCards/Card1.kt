package com.example.eventpassfront.ui.components.eventCards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.eventpassfront.ui.components.animation.LivePulseDot
import com.example.eventpassfront.ui.theme.DeepOrange
import com.example.eventpassfront.ui.theme.SurfaceDark
import com.example.eventpassfront.ui.theme.TextSecondary

//TODO onRegister
@Composable
fun Card1(
    title: String,
    location: String,
    date: String,
    imageUrl: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceDark)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    //TODO PONER IMG
                    Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray))

                    Surface(
                        modifier = Modifier.padding(16.dp),
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "ENTRADAS VOLANDO",
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                    ) {
                        LivePulseDot()
                    }
                }

                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = null,
                            tint = DeepOrange,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = " $location",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }

                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        modifier = Modifier.padding(top = 4.dp, start = 20.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Botón Registrarme (Ancho completo)
                    Button(
                        onClick = { /*onRegisterClick*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DeepOrange),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "Registrarme",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}