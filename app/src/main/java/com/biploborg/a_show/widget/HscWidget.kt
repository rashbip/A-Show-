package com.biploborg.a_show.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.biploborg.a_show.R
import com.biploborg.a_show.domain.usecase.GetCountdownUseCase
import java.time.LocalDate

class HscWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            WidgetContent(context)
        }
    }

    @Composable
    private fun WidgetContent(context: Context) {
        val useCase = GetCountdownUseCase()
        val countdown = useCase.execute()
        val daysInBangla = useCase.getBanglaNumber(countdown.days)
        
        val today = LocalDate.now().dayOfWeek.value // 1 (Mon) to 7 (Sun)
        
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(ColorProvider(Color(0xFF1B3E7A))) // Dark blue background
                .padding(16.dp)
        ) {
            // Weekday Circles
            Row(
                modifier = GlanceModifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                val banglaDays = listOf("সোম", "মঙ্গল", "বুধ", "বৃহ", "শুক্র")
                banglaDays.forEachIndexed { index, day ->
                    val isChecked = (index + 1) <= (today % 5 + 1) // Simple check logic
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = GlanceModifier.padding(end = 8.dp)) {
                        Text(
                            text = day,
                            style = TextStyle(color = ColorProvider(Color.White), fontSize = 10.sp)
                        )
                        Box(
                            modifier = GlanceModifier
                                .size(16.dp)
                                .background(if (isChecked) Color(0xFF00C853) else Color.Transparent),
                            contentAlignment = Alignment.Center
                        ) {
                            if (!isChecked) {
                                Box(modifier = GlanceModifier.size(14.dp).background(Color.White)) {}
                            }
                        }
                    }
                }
            }

            Spacer(modifier = GlanceModifier.height(16.dp))

            Row(
                modifier = GlanceModifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = GlanceModifier.defaultWeight()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            provider = ImageProvider(R.drawable.study_character), // We'll use this for the icon too
                            contentDescription = null,
                            modifier = GlanceModifier.size(24.dp)
                        )
                        Spacer(modifier = GlanceModifier.width(8.dp))
                        Text(
                            text = "$daysInBangla দিন",
                            style = TextStyle(
                                color = ColorProvider(Color.White),
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Text(
                        text = "চালিয়ে যাও",
                        style = TextStyle(color = ColorProvider(Color.White.copy(alpha = 0.8f)), fontSize = 14.sp)
                    )
                }

                Image(
                    provider = ImageProvider(R.drawable.study_character),
                    contentDescription = null,
                    modifier = GlanceModifier.size(100.dp)
                )
            }
        }
    }
}
