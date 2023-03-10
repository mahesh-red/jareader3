package com.example.jareader3.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jareader3.screens.ReaderSplashscreen
import com.example.jareader3.screens.home.Home
import com.example.jareader3.screens.login.ReaderLoginScreen

@Composable
fun ReaderNavigation(){
    val navController= rememberNavController( )
    NavHost(navController=navController,
    startDestination = ReaderScreens.SplashScreen.name){
        composable(ReaderScreens.SplashScreen.name){
            ReaderSplashscreen(navController = navController)
        }
        composable(ReaderScreens.LoginScreen.name){
            ReaderLoginScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderHomeScreen.name){
            Home(navController = navController)
        }

    }
}