package com.example.payten_windowsxp_userapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.payten_windowsxp_userapp.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val poppinsRegular = TextStyle(fontFamily = FontFamily(Font(R.font.poppins_regular)))
val poppinsBold = TextStyle(fontFamily = FontFamily(Font(R.font.poppins_bold)))
val poppinsItalic = TextStyle(fontFamily = FontFamily(Font(R.font.poppins_italic)))
val poppinsThin = TextStyle(fontFamily = FontFamily(Font(R.font.poppins_thin)))
val poppinsMedium = TextStyle(fontFamily = FontFamily(Font(R.font.poppins_medium)))
val poppinsLight = TextStyle(fontFamily = FontFamily(Font(R.font.poppins_light)))