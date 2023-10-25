package com.pnj.jetbraintoolbox.model

import androidx.annotation.DrawableRes

data class JetbrainsTool(
    val name: String,
    @DrawableRes val imageResource: Int,
    val description: String = "",
)
