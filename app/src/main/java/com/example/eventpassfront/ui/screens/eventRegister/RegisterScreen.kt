package com.example.eventpassfront.ui.screens.eventRegister

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventpassfront.ui.components.errors.ErrorText
import com.example.eventpassfront.ui.components.fields.CustomTextField
import com.example.eventpassfront.ui.theme.DeepOrange
import com.example.eventpassfront.ui.utils.getDrawableId
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    modifier: Modifier,
    eventId: Int,
    viewModel: EventRegisterViewModel = viewModel(),
    onRegistrationSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.loadEvent(eventId)
    }

    LaunchedEffect(state.isSuccess, state.errorMessage) {
        if (state.isSuccess) {
            scope.launch {
                snackbarHostState.showSnackbar("¡Registro exitoso!")
            }
            delay(2000)
            onRegistrationSuccess()
        } else if (state.errorMessage != null) {
            scope.launch {
                snackbarHostState.showSnackbar("Error: ${state.errorMessage}")
            }
        }
    }

    Box(modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            state.evento?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {
                    Image(
                        painter = painterResource(id = getDrawableId(it.imagenRes)),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.4f))
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Surface(
                            color = DeepOrange,
                            shape = CircleShape
                        ) {
                            Text(
                                text = it.categoria,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White
                            )
                        }

                        Spacer(Modifier.height(10.dp))

                        Text(
                            text = it.titulo,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    "Asegura tu lugar en el evento",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Completa el formulario a continuación para registrarte.",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(28.dp))

                CustomTextField(
                    label = "Nombre Completo",
                    placeHolder = "Introduce tu nombre completo",
                    value = state.nombre,
                    onValueChange = { viewModel.updateName(it) },
                    icon = Icons.Default.Person
                )

                state.nombreError?.let { ErrorText(it) }
                Spacer(Modifier.height(16.dp))

                CustomTextField(
                    label = "Correo Electrónico",
                    placeHolder = "Introduce tu correo electrónico",
                    value = state.email,
                    onValueChange = { viewModel.updateEmail(it) },
                    icon = Icons.Default.Email
                )

                state.emailError?.let { ErrorText(it) }
                Spacer(Modifier.height(16.dp))

                CustomTextField(
                    label = "Número de Teléfono",
                    placeHolder = "Introduce tu número de teléfono",
                    value = state.telefono,
                    onValueChange = { viewModel.updateTelefono(it) },
                    icon = Icons.Default.Phone,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                state.telefonoError?.let { ErrorText(it) }

                Spacer(Modifier.height(32.dp))
            }
        }


        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = 0.9f)
        ) {
            Button(
                onClick = { viewModel.registerAssistant(eventId) },
                enabled = !state.isLoading && !state.isSuccess && viewModel.isFormValid(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp)
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DeepOrange,
                    contentColor = Color.White,
                    disabledContainerColor = Color.DarkGray,
                    disabledContentColor = Color.Gray
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(
                        "Registrarse",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) { data ->
            Snackbar(
                snackbarData = data,
                containerColor = if (state.isSuccess) Color(0xFF2E7D32) else Color(0xFFB00020),
                contentColor = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}
