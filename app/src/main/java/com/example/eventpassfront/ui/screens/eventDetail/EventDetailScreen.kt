package com.example.eventpassfront.ui.screens.eventDetail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.eventpassfront.ui.components.detailItems.DetailItem
import com.example.eventpassfront.ui.theme.DeepOrange
import com.example.eventpassfront.ui.utils.formatEventDate
import com.example.eventpassfront.ui.utils.getDrawableId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventDetailScreen(
    modifier: Modifier,
    eventId: Int,
    navController: NavController,
    viewModel: EventDetailViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(eventId) {
        viewModel.loadEvent(eventId)
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = DeepOrange)
        } else if (state.errorMessage != null) {
            Text(text = state.errorMessage!!, color = Color.Red, modifier = Modifier.align(Alignment.Center))
        } else {
            state.evento?.let { it ->
                Column(modifier = Modifier.fillMaxSize()) {

                    LazyColumn(modifier = Modifier.weight(1f)) {
                        item {
                            Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                                Image(
                                    painter = painterResource(id = getDrawableId(it.imagenRes)),
                                    contentDescription = "Image event",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        item {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Surface(
                                    color = DeepOrange.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = it.categoria,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = DeepOrange,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(Modifier.size(12.dp))
                                Text(
                                    text = it.titulo,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(Modifier.height(24.dp))
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E)),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        DetailItem(Icons.Default.DateRange, formatEventDate(it.fecha))
                                        HorizontalDivider(color = Color.DarkGray, modifier = Modifier.padding(vertical = 12.dp))
                                        DetailItem(Icons.Default.LocationOn, it.ubicacion)
                                    }
                                }
                            }
                        }

                        item {
                            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                                Text(
                                    text = "Sobre el evento",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(Modifier.height(12.dp))
                                Text(
                                    text = it.descripcion,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray,
                                    lineHeight = 24.sp
                                )
                                Spacer(Modifier.height(20.dp))
                            }
                        }
                    }

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Black
                    ) {
                        Button(
                            onClick = { navController.navigate("register/${it.id}") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .height(54.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = DeepOrange),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(text = "Registrarse", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}