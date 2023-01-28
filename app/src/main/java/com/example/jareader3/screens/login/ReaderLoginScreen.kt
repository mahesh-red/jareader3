package com.example.jareader3.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
//import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jareader3.R
import com.example.jareader3.components.EmailInput
import com.example.jareader3.components.PasswordInput
import com.example.jareader3.components.ReaderLogo
import com.example.jareader3.components.SubmitButton
import com.example.jareader3.navigation.ReaderScreens
import org.checkerframework.checker.units.qual.s


@Composable
fun ReaderLoginScreen(navController: NavController,
viewModel: LoginScreenViewModel= androidx.lifecycle.viewmodel.compose.viewModel()) {
    val showLoginForm = rememberSaveable { mutableStateOf(true) }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ReaderLogo()
            if (showLoginForm.value) UserForm(loading = false,isCreateAccount = false){email,password->
                viewModel.signwithemailandpassword(email, password){
                    navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                }
            }
            else{
                UserForm(loading = false,isCreateAccount = true){email,password->
                    viewModel.createuserwithemailamdpassword(email,password){
                        navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                    }
                }
            }

        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.padding(15.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
            val text:String = if(showLoginForm.value) "Sign Up" else "Login"
            Text(text = "New User ?")
            Text(text, modifier = Modifier
                .clickable {
                    showLoginForm.value = !showLoginForm.value
                }
                .padding(5.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.secondaryVariant


            )
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    loading:Boolean=false,
    isCreateAccount:Boolean=false,
    onDone:(String,String)->Unit={email,pws->}
){

    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest=FocusRequester.Default
    val keyboardController=LocalSoftwareKeyboardController.current
    val valid = remember(email.value,password.value) {
        email.value.trim().isNotEmpty()&& password.value.trim().isNotEmpty()
    }
    val modifier= Modifier
        .height(250.dp)
        .background(MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState())
    Column(modifier,
    horizontalAlignment = Alignment.CenterHorizontally) {
        if (isCreateAccount) Text(text = stringResource(id = R.string.create_acct),
        modifier = Modifier.padding(4.dp)) else Text(text = "")
        EmailInput(emailState = email, enabled = true, onAction = KeyboardActions {
            passwordFocusRequest.requestFocus()
        })

        PasswordInput(modifier=Modifier.focusRequester(passwordFocusRequest),
            passwordState=password,
            labelId="Password",
            enabled=true,
            passwordVisibility = passwordVisibility,
            onAction=KeyboardActions{
                if(!valid) return@KeyboardActions
                onDone(email.value.trim(),password.value.trim()) }
        )
        SubmitButton(textId =if (isCreateAccount) "Create Account" else "Login" , loading =loading , validInputs =valid ) {
            onDone(email.value.trim(),password.value.trim())
            keyboardController?.hide()

        }
    }
}







