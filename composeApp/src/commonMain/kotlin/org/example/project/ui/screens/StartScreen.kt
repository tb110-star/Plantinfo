package org.example.project.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "Scanner")
    val logoPainter = painterResource(Res.drawable.logo)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background( Color(0xFF334234)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(contentAlignment = Alignment.Center) {
            val scanLineY by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 2500, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "scanLineY"
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
                    .padding(horizontal = 24.dp)

            ) {
                Image(
                    painter = logoPainter,
                    contentDescription = "Logo",
                    modifier = Modifier
                        .fillMaxSize()

                        .clip(RoundedCornerShape(45.dp)),
                    contentScale = ContentScale.Crop
                )

                Canvas(modifier = Modifier.fillMaxSize()) {
                    val y = size.height * scanLineY
                    val horizontalPadding = 20.dp.toPx()
                    val verticalPadding = 20.dp.toPx()
                    if (y in verticalPadding..(size.height - verticalPadding)) {
                        drawLine(
                            color = Color.White.copy(alpha = 0.5f),
                            start = androidx.compose.ui.geometry.Offset(horizontalPadding, y),
                            end = androidx.compose.ui.geometry.Offset(size.width - horizontalPadding, y),
                            strokeWidth = 3.dp.toPx()
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Plantfo",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xE9B4DCBB),
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onStartClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xE9B4DCBB)),
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .height(50.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Start", fontSize = 28.sp, color = Color.White)
        }
    }
}
