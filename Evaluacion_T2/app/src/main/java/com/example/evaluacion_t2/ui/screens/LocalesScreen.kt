package com.example.evaluacion_t2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.evaluacion_t2.data.local.UserEntity
import com.example.evaluacion_t2.ui.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalesScreen(
    viewModel: UserViewModel,
    onNavigateToNewUser: () -> Unit,
    onNavigateToEditUser: (UserEntity) -> Unit
) {
    val localUsers by viewModel.localUsers.collectAsState()
    var userToDelete by remember { mutableStateOf<UserEntity?>(null) }

    if (userToDelete != null) {
        DeleteConfirmationDialog(
            onConfirm = {
                viewModel.deleteUser(userToDelete!!) {
                    userToDelete = null
                }
            },
            onDismiss = {
                userToDelete = null
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Usuarios locales") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToNewUser) {
                Text("+", style = MaterialTheme.typography.titleLarge)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (localUsers.isEmpty()) {
                Text(
                    text = "No hay usuarios registrados",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(localUsers) { user ->
                        LocalUserItem(
                            user = user,
                            onEdit = { onNavigateToEditUser(user) },
                            onDelete = { userToDelete = user }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun LocalUserItem(
    user: UserEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Usuario: ${user.username}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Correo: ${user.email}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(onClick = onEdit) {
                    Text("Editar")
                }
                Spacer(modifier = Modifier.padding(start = 8.dp))
                OutlinedButton(
                    onClick = onDelete,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Eliminar")
                }
            }
        }
    }
}