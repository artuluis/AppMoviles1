package com.example.hotelvalentin.ui.screens.client

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.RoomService
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hotelvalentin.R
import com.example.hotelvalentin.ui.theme.HotelGold
import com.example.hotelvalentin.ui.theme.HotelNavy
import com.example.hotelvalentin.ui.theme.HotelSlate

data class HotelServiceItem(
    val title: String,
    val subtitle: String,
    val hours: String,
    val icon: ImageVector,
    val imageRes: Int? = null,
    val isAvailable: Boolean = true
)

@Composable
fun ServicesTab(
    onShowMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val services = listOf(
        HotelServiceItem(
            title = "Infinity Rooftop Pool & Spa",
            subtitle = "Piscina infinita panorámica en la terraza superior con camas balinesas, sauna húmedo y masajes terapéuticos.",
            hours = "08:00 AM - 10:00 PM",
            icon = Icons.Default.Pool,
            imageRes = R.drawable.hotel_pool
        ),
        HotelServiceItem(
            title = "Restaurante Gourmet Valentín",
            subtitle = "Cocina internacional de autor y fusión peruana contemporánea galardonada con estrellas.",
            hours = "06:30 AM - 11:30 PM",
            icon = Icons.Default.Restaurant
        ),
        HotelServiceItem(
            title = "Skyline Lounge & Cocktail Bar",
            subtitle = "Coctelería de autor premium, cava de vinos selectos y música lounge acústica al atardecer.",
            hours = "05:00 PM - 02:00 AM",
            icon = Icons.Default.LocalBar
        ),
        HotelServiceItem(
            title = "Room Service & Concierge 24/7",
            subtitle = "Servicio a la habitación todo el día con menú completo, desayuno a la cama y asistencia de equipaje.",
            hours = "Atención 24 Horas",
            icon = Icons.Default.RoomService
        ),
        HotelServiceItem(
            title = "Centro Wellness & Fitness",
            subtitle = "Gimnasio equipado con tecnología Technogym, clases de yoga matutinas y circuito termal.",
            hours = "06:00 AM - 10:00 PM",
            icon = Icons.Default.Spa
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = "Servicios & Experiencias",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = HotelNavy
                )
                Text(
                    text = "Comodidades exclusivas para nuestros distinguidos huéspedes",
                    fontSize = 13.sp,
                    color = HotelSlate
                )
            }
        }

        items(services.size) { index ->
            val service = services[index]
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    // Si tiene imagen (ej. la piscina)
                    if (service.imageRes != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                        ) {
                            Image(
                                painter = painterResource(id = service.imageRes),
                                contentDescription = service.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                                        )
                                    )
                            )
                        }
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(HotelGold.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = service.icon,
                                    contentDescription = null,
                                    tint = HotelGold,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = service.title,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = HotelNavy
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(top = 2.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AccessTime,
                                        contentDescription = null,
                                        tint = HotelSlate,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = service.hours,
                                        fontSize = 11.sp,
                                        color = HotelSlate
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = service.subtitle,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = { onShowMessage("Solicitud enviada para: ${service.title}") },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = HotelNavy,
                                contentColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Solicitar Servicio", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}
