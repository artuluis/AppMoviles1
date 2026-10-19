package com.example.hotelvalentin.ui.screens.admin

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hotelvalentin.model.GuestCheckIn
import com.example.hotelvalentin.model.HotelData
import com.example.hotelvalentin.model.Room
import com.example.hotelvalentin.model.User
import com.example.hotelvalentin.ui.theme.GreenSuccess
import com.example.hotelvalentin.ui.theme.HotelGold
import com.example.hotelvalentin.ui.theme.HotelNavy
import com.example.hotelvalentin.ui.theme.HotelSlate
import kotlinx.coroutines.launch

@Composable
fun AdminPanelScreen(
    user: User,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedAdminTab by remember { mutableIntStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val showMessage: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HotelNavy)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = HotelGold,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "ADMIN",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Panel de Control",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "Hotel Valentín • ${user.name}",
                            color = Color.White.copy(alpha = 0.75f),
                            fontSize = 12.sp
                        )
                    }

                    IconButton(
                        onClick = onLogout,
                        modifier = Modifier
                            .size(38.dp)
                            .background(Color.White.copy(alpha = 0.15f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Cerrar Sesión",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 3 Pestañas del Admin: Gestión de Cuartos, Reporte de Ingresos, Check-in / Huéspedes
                val adminTabs = listOf(
                    "Gestión de Cuartos",
                    "Reporte Ingresos",
                    "Check-in / Huéspedes"
                )

                ScrollableTabRow(
                    selectedTabIndex = selectedAdminTab,
                    containerColor = HotelNavy,
                    contentColor = HotelGold,
                    edgePadding = 0.dp,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedAdminTab]),
                            color = HotelGold,
                            height = 3.dp
                        )
                    }
                ) {
                    adminTabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedAdminTab == index,
                            onClick = { selectedAdminTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 12.sp,
                                    fontWeight = if (selectedAdminTab == index) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedAdminTab == index) HotelGold else Color.White.copy(alpha = 0.7f)
                                )
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedAdminTab) {
                0 -> AdminRoomsTab(onShowMessage = showMessage)
                1 -> AdminRevenueTab()
                2 -> AdminGuestsTab(onShowMessage = showMessage)
            }
        }
    }
}

// 1. Gestión de Cuartos
@Composable
fun AdminRoomsTab(
    onShowMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val rooms = HotelData.sampleRooms

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Inventario de Habitaciones",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = HotelNavy
                    )
                    Text(
                        text = "Total: ${HotelData.totalRooms} habitaciones registradas",
                        fontSize = 12.sp,
                        color = HotelSlate
                    )
                }

                Surface(
                    color = HotelGold.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "${HotelData.occupiedRooms} Ocupadas / ${HotelData.totalRooms - HotelData.occupiedRooms} Libres",
                        color = HotelNavy,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        items(rooms) { room ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = room.imageRes),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = room.name,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = HotelNavy,
                            maxLines = 1
                        )
                        Text(
                            text = "Categoría: ${room.category} • Tarifa: $${room.pricePerNight} USD",
                            fontSize = 11.sp,
                            color = HotelSlate
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = if (room.isAvailable) GreenSuccess.copy(alpha = 0.15f) else Color(0xFFEF4444).copy(alpha = 0.15f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = if (room.isAvailable) "Disponible" else "Mantenimiento / Reservada",
                                    color = if (room.isAvailable) GreenSuccess else Color(0xFFEF4444),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            val newStatus = !room.isAvailable
                            val index = rooms.indexOf(room)
                            if (index != -1) {
                                rooms[index] = room.copy(isAvailable = newStatus)
                                onShowMessage("Estado cambiado para '${room.name}' a ${if (newStatus) "Disponible" else "En Mantenimiento"}")
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (room.isAvailable) HotelNavy else HotelGold
                        ),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text(
                            text = if (room.isAvailable) "Pausar" else "Activar",
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

// 2. Reporte de Ingresos
@Composable
fun AdminRevenueTab(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Métricas Financieras del Mes",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = HotelNavy
            )
            Text(
                text = "Corte contable: Octubre 2026",
                fontSize = 12.sp,
                color = HotelSlate
            )
        }

        // Tarjeta Principal Ingresos
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = HotelNavy),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Ingresos Totales (Mes Actual)",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 12.sp
                        )
                        Surface(
                            color = GreenSuccess.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.TrendingUp,
                                    contentDescription = null,
                                    tint = GreenSuccess,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "+14.8%",
                                    color = GreenSuccess,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "$48,250.00 USD",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = HotelGold
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = Color.White.copy(alpha = 0.15f))
                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Tasa de Ocupación", color = Color.White.copy(alpha = 0.7f), fontSize = 11.sp)
                            Text("87.4%", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                        Column {
                            Text("Tarifa Media Diaria (ADR)", color = Color.White.copy(alpha = 0.7f), fontSize = 11.sp)
                            Text("$195.00 USD", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                        Column {
                            Text("RevPAR", color = Color.White.copy(alpha = 0.7f), fontSize = 11.sp)
                            Text("$170.43 USD", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                }
            }
        }

        // Desglose por Categoría
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Ventas por Categoría de Habitación",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = HotelNavy
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    val categoriesRevenue = listOf(
                        Triple("Suites Deluxe", "$24,800 USD", 0.51f),
                        Triple("Suite Presidencial", "$15,800 USD", 0.33f),
                        Triple("Habitación Estándar", "$7,650 USD", 0.16f)
                    )

                    categoriesRevenue.forEach { item ->
                        Column(modifier = Modifier.padding(vertical = 6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(item.first, fontSize = 12.sp, color = HotelNavy, fontWeight = FontWeight.SemiBold)
                                Text(item.second, fontSize = 12.sp, color = HotelGold, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { item.third },
                                color = HotelGold,
                                trackColor = Color.LightGray.copy(alpha = 0.3f),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp))
                            )
                        }
                    }
                }
            }
        }
    }
}

// 3. Check-in / Huéspedes
@Composable
fun AdminGuestsTab(
    onShowMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val guests = HotelData.activeGuests

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Control de Huéspedes",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = HotelNavy
                    )
                    Text(
                        text = "${guests.size} registros en lista de recepción",
                        fontSize = 12.sp,
                        color = HotelSlate
                    )
                }

                Surface(
                    color = HotelGold.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Recepción 24/7",
                        color = HotelNavy,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        items(guests) { guest ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = guest.guestName,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = HotelNavy
                        )

                        Surface(
                            color = when (guest.status) {
                                "En Estadía" -> GreenSuccess.copy(alpha = 0.15f)
                                "Check-in Pendiente" -> HotelGold.copy(alpha = 0.2f)
                                else -> Color(0xFF3B82F6).copy(alpha = 0.15f)
                            },
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = guest.status,
                                color = when (guest.status) {
                                    "En Estadía" -> GreenSuccess
                                    "Check-in Pendiente" -> HotelGold
                                    else -> Color(0xFF3B82F6)
                                },
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "${guest.roomNumber} • ${guest.roomType}",
                        fontSize = 12.sp,
                        color = HotelSlate
                    )

                    Text(
                        text = "Estadía: ${guest.checkInDate} al ${guest.checkOutDate}",
                        fontSize = 11.sp,
                        color = HotelSlate.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        OutlinedButton(
                            onClick = {
                                onShowMessage("Acción procesada para ${guest.guestName} (${guest.roomNumber})")
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Icon(Icons.Default.HowToReg, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (guest.status == "Check-in Pendiente") "Check-in" else "Check-out",
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
