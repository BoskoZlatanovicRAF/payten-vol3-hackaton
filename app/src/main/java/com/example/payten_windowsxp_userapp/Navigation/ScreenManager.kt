package com.example.payten_windowsxp_userapp.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.payten_windowsxp_userapp.Login.logIn
import com.example.payten_windowsxp_userapp.Registration.registerScreen
import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.EditLocalScreen.editLocalScreen
import com.example.payten_windowsxp_userapp.Users.Admin.LocalScreen.localScreen
import com.example.payten_windowsxp_userapp.Users.Admin.adminHomeScreen
import com.example.payten_windowsxp_userapp.Users.user.QR.generateQRScreen
import com.example.payten_windowsxp_userapp.Users.user.userhomescreen.userHomeScreen

@Composable
fun ScreenManager() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        logIn(
            route = "login",
            onUserClick = {
                when (it) {
                    "registerScreen" -> navController.navigate(route = "registerScreen")
                    "adminHomeScreen" -> navController.navigate(route = "adminHomeScreen")
                    "userHomeScreen" -> navController.navigate(route = "userHomeScreen")
                    else -> navController.navigate(route = "login")
                }
            }
        )
        registerScreen(
            route = "registerScreen",
            onItemClick = {
                navController.navigate(route = "userHomeScreen")
            },
            onLoginClick = {
                navController.navigate(route = "login")
            }
        )
        userHomeScreen(
            route = "userHomeScreen",
            onBonusClick = {
                navController.navigate(route = "")//dodaj rutu za points screen
            },
            onQrClick = {
                navController.navigate(route = "qrScreen")
            }
        )
        generateQRScreen(
            route = "qrScreen",
            onBackClick = {
                navController.navigateUp();
            }
        )
        adminHomeScreen(
            route = "adminHomeScreen",
            onItemClick = {
                navController.navigate(route = "localScreen/${it}")
            },
        )
        localScreen(
            route = "localScreen/{localId}",
            onItemClick = {
                navController.navigate(route = "EditLocal/${it}")
            },
        )
        editLocalScreen(
            route = "editLocalScreen/{editLocalId}",
        )
    }
}

inline val SavedStateHandle.localId: Long
    get() = checkNotNull(get("localId")) { "localId is mandatory" }

inline val SavedStateHandle.editLocalId: Long
    get() = checkNotNull(get("editLocalId")) { "editLocalId is mandatory" }