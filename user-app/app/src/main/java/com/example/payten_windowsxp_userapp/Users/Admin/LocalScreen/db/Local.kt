package com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "locals"
)
data class Local (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val address: String,
    val boxNumber: Int,
)