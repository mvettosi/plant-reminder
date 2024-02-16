/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.mvettosi.plantreminder.presentation.shared.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.github.mvettosi.plantreminder.presentation.shared.R

// Set of Material typography styles to start with
val fontFamily =
    FontFamily(
        Font(resId = R.font.poppins_medium, weight = FontWeight.Normal),
        Font(resId = R.font.poppins_regular, weight = FontWeight.Medium),
        Font(resId = R.font.poppins_semi_bold, weight = FontWeight.SemiBold))

val Typography =
    Typography(
        headlineLarge =
            TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.adjust().sp,
                lineHeight = 32.adjust().sp,
            ),
        bodyLarge =
            TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.adjust().sp,
                lineHeight = 24.adjust().sp,
            ),
        bodyMedium =
            TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.adjust().sp,
                lineHeight = 20.adjust().sp,
            ),
        bodySmall =
            TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.adjust().sp,
                lineHeight = 20.adjust().sp,
            ),
        labelMedium =
            TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 12.adjust().sp,
                lineHeight = 16.adjust().sp,
            ),
        labelSmall =
            TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 10.adjust().sp,
                lineHeight = 16.adjust().sp,
            ),
    )

private fun Int.adjust() = this
