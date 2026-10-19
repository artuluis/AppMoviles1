package com.example.hotelvalentin.ui.screens.client

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hotelvalentin.model.HotelData
import com.example.hotelvalentin.model.Reservation
import com.example.hotelvalentin.model.Room
import com.example.hotelvalentin.model.User
import com.example.hotelvalentin.ui.components.HotelBannerCard
import com.example.hotelvalentin.ui.components.RoomCard
import com.example.hotelvalentin.ui.theme.HotelGold
import com.example.hotelvalentin.ui.theme.HotelNavy
import com.example.hotelvalentin.ui.theme.HotelSlate

@Composable
fun CatalogTab(
    user: User,
    onShowMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todas") }
    var selectedRoomForBooking by remember { mutableStateOf<Room?>(null) }

    val filteredRooms = remember(searchQuery, selectedCategory) {
        HotelData.sampleRooms.filter { room ->
            val matchesCategory = (selectedCategory == "Todas" || room.category.equals(selectedCategory, ignoreCase = true))
            val matchesSearch = room.name.contains(searchQuery, ignoreCase = true) ||
                    room.description.contains(searchQuery, ignoreCase = true) ||
                    room.category.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 24.dp)
    ) {
        // 1. Encabezado con perfil y notificaciones
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Bienvenido, ${user.name}",
                        fontSize = 13.sp,
                        color = HotelSlate
                    )
                    Text(
                        text = "Hotel Valentín ★★★★★",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = HotelNavy
                    )
                }

                IconButton(
                    onClick = { onShowMessage("No tienes notificaciones pendientes") },
                    modifier = Modifier
                        .size(42.dp)
                        .background(MaterialTheme.colorScheme.surface, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notificaciones",
                        tint = HotelNavy
                    )
                }
            }
        }

        // 2. Buscador
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar suite, tipo de cama o servicios...", fontSize = 13.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = HotelGold
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = HotelGold,
                    unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }

        // 3. Banner Hero Principal
        item {
            HotelBannerCard(modifier = Modifier.padding(bottom = 20.dp))
        }

        // 4. Selector de Categorías
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Nuestras Habitaciones",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = HotelNavy
                )
                Text(
                    text = "${filteredRooms.size} disponibles",
                    fontSize = 12.sp,
                    color = HotelSlate
                )
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                items(HotelData.categories) { category ->
                    val isSelected = selectedCategory == category
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = category },
                        label = {
                            Text(
                                text = category,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = HotelNavy,
                            selectedLabelColor = Color.White,
                            containerColor = MaterialTheme.colorScheme.surface,
                            labelColor = HotelNavy
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            }
        }

        // 5. Lista de Habitaciones
        if (filteredRooms.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 36.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay habitaciones para los filtros seleccionados.",
                        color = HotelSlate,
                        fontSize = 13.sp
                    )
                }
            }
        } else {
            items(filteredRooms, key = { it.id }) { room ->
                RoomCard(
                    room = room,
                    onBookClick = { selectedRoomForBooking = it }
                )
            }
        }
    }

    // Modal para Reservar
    selectedRoomForBooking?.let { room ->
        AlertDialog(
            onDismissRequest = { selectedRoomForBooking = null },
            icon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = HotelGold,
                    modifier = Modifier.size(32.dp)
                )
            },
            title = {
                Text(
                    text = "Confirmar Reserva",
                    fontWeight = FontWeight.Bold,
                    color = HotelNavy
                )
            },
            text = {
                Column {
                    Text(
                        text = room.name,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = HotelNavy
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Tarifa: $${"%.2f".format(room.pricePerNight)} USD por noche",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = HotelGold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Huésped principal: ${user.name}\nFechas: 24 al 27 Octubre 2026 (3 noches)\nTotal estimado: $${"%.2f".format(room.pricePerNight * 3)} USD",
                        fontSize = 12.sp,
                        color = HotelSlate
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val newReservation = Reservation(
                            id = "HV-2026-${(100..999).random()}",
                            room = room,
                            guestName = user.name,
                            checkInDate = "24 Oct 2026",
                            checkOutDate = "27 Oct 2026",
                            nights = 3,
                            totalAmount = room.pricePerNight * 3,
                            status = "Confirmada"
                        )
                        HotelData.userReservations.add(0, newReservation)
                        selectedRoomForBooking = null
                        onShowMessage("¡Reserva confirmada! Añadida a 'Mis Reservas'.")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HotelNavy)
                ) {
                    Text("Confirmar y Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedRoomForBooking = null }) {
                    Text("Cancelar", color = HotelSlate)
                }
            }
        )
    }
}
