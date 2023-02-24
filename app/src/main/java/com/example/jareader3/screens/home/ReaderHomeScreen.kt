package com.example.jareader3.screens.home

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.I
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jareader3.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Home(navController: NavController) {
Scaffold(topBar = {
                  ReaderAppBar(title = "A.Reader", navController =navController )
},
    floatingActionButton = { FABContent {
}
}) {
    //conetnt
    Surface(modifier = Modifier.fillMaxSize()) {
        //homeContent


    }
}
}

@Composable
fun FABContent(onTap:()->Unit){
    FloatingActionButton(onClick = { onTap() },
    shape = RoundedCornerShape(50.dp),
    backgroundColor = Color(0xFF92CBDF)
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription ="Add a Book" ,
        tint = Color.White)
        
    }

}

//Top Bar
@Composable
fun ReaderAppBar(
    title: String,
    showProfile:Boolean=true,
    navController: NavController
){
    TopAppBar(title={
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (showProfile){
                            Icon(imageVector = Icons.Default.Favorite ,
                                contentDescription = "Logo Icon",
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .scale(0.6f))
                        }
                        Text(text = title,
                        color = Color.Red.copy(alpha = 0.7f),
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        )

                    }
    },
        actions = {
            IconButton(onClick = {FirebaseAuth.getInstance().signOut().run {
                navController.navigate(ReaderScreens.LoginScreen.name)}}) {
                Icon(imageVector =Icons.Filled.Logout , contentDescription ="LogOut"
                //tint = Color.Green.copy(0.4f)
            )

                }

        },
    backgroundColor = Color.Transparent,
        elevation = 0.dp)


}