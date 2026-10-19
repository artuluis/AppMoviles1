package com.example.hotelvalentin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.RoomService
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hotelvalentin.model.HotelData
import com.example.hotelvalentin.model.Room
import com.example.hotelvalentin.ui.components.HotelBannerCard
import com.example.hotelvalentin.ui.components.RoomCard
import com.example.hotelvalentin.ui.theme.HotelGold
import com.example.hotelvalentin.ui.theme.HotelNavy
import com.example.hotelvalentin.ui.theme.HotelSlate
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todas") }
    var selectedRoomForBooking by remember { mutableStateOf<Room?>(null) }
    var selectedNavIndex by remember { mutableIntStateOf(0) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Filtrar habitaciones por categoría y búsqueda
    val filteredRooms = remember(searchQuery, selectedCategory) {
        HotelData.sampleRooms.filter { room ->
            val matchesCategory = (selectedCategory == "Todas" || room.category.equals(selectedCategory, ignoreCase = true))
            val matchesSearch = room.name.contains(searchQuery, ignoreCase = true) ||
                    room.description.contains(searchQuery, ignoreCase = true) ||
                    room.category.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                val navItems = listOf(
                    Pair("Inicio", Icons.Default.Home),
                    Pair("Reservas", Icons.Outlined.BookmarkBorder),
                    Pair("Servicios", Icons.Default.Spa),
                    Pair("Perfil", Icons.Default.Person)
                )

                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedNavIndex == index,
                        onClick = { selectedNavIndex = index },
                        icon = { Icon(item.second, contentDescription = item.first) },
                        label = { Text(item.first, fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = HotelGold,
                            selectedTextColor = HotelNavy,
                            indicatorColor = HotelGold.copy(alpha = 0.15f),
                            unselectedIconColor = HotelSlate,
                            unselectedTextColor = HotelSlate
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // 1. Encabezado de Usuario y Logo
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
                            text = "Bienvenido a",
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
                        onClick = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("No tienes notificaciones pendientes")
                            }
                        },
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

            // 2. Barra de Búsqueda
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Buscar suite, servicios o amenidades...", fontSize = 13.sp) },
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

            // 3. Banner Hero Principal con Foto de Fachada
            item {
                HotelBannerCard(modifier = Modifier.padding(bottom = 20.dp))
            }

            // 4. Servicios Rápidos (Amenities)
            item {
                Text(
                    text = "Servicios Exclusivos",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = HotelNavy,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(bottom = 20.dp)
                ) {
                    val quickServices = listOf(
                        Triple("Piscina & Spa", Icons.Default.Pool, "Infinity Rooftop"),
                        Triple("Gourmet", Icons.Default.Restaurant, "Restaurante 5 Estrellas"),
                        Triple("Room Service", Icons.Default.RoomService, "Atención 24/7"),
                        Triple("Wellness", Icons.Default.Spa, "Masajes y Sauna")
                    )

                    items(quickServices) { service ->
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.clickable {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Servicio: ${service.first} - ${service.third}")
                                }
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(HotelGold.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = service.second,
                                        contentDescription = service.first,
                                        tint = HotelGold,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = service.first,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = HotelNavy
                                    )
                                    Text(
                                        text = service.third,
                                        fontSize = 11.sp,
                                        color = HotelSlate
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 5. Chips de Filtro por Categoría
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

            // 6. Lista de Tarjetas de Habitaciones
            if (filteredRooms.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No se encontraron habitaciones con esos criterios.",
                            color = HotelSlate,
                            fontSize = 14.sp
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

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // Diálogo de Confirmación de Reserva
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
                    text = "Reservar Habitación",
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
                        text = "Categoría: ${room.category} • ${room.maxGuests} personas máx.",
                        fontSize = 12.sp,
                        color = HotelSlate
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Tarifa por noche: $${"%.2f".format(room.pricePerNight)} USD",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = HotelGold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "¿Deseas proceder con el registro de reserva para esta habitación en Hotel Valentín?",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val bookedRoomName = room.name
                        selectedRoomForBooking = null
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("¡Reserva confirmada con éxito para $bookedRoomName!")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HotelNavy)
                ) {
                    Text("Confirmar")
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
