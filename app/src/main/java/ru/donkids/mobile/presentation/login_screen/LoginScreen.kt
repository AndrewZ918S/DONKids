package ru.donkids.mobile.presentation.login_screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.donkids.mobile.R
import ru.donkids.mobile.presentation.ui.theme.DONKidsTheme
import ru.donkids.mobile.util.LocalText
import ru.donkids.mobile.util.ResText

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel<LoginViewModelImpl>()) {
    val context = LocalContext.current
    val state = viewModel.state

    LaunchedEffect(key1 = context) {
        viewModel.serverEvents.collect {
            Toast.makeText(
                context,
                it?.name,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Surface(Modifier.fillMaxSize()) {
        Column(Modifier.padding(32.dp)) {
            Spacer(Modifier.weight(2f))
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null,
                modifier = Modifier
                    .size(96.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(32.dp))
            LocalText(
                resId = R.string.auth,
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 36.sp
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.weight(3f))
            OutlinedTextField(
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(LoginFormEvent.EmailChanged(it))
                },
                isError = state.emailError != null,
                label = {
                    LocalText(R.string.email)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = state.emailError?.getText() ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(LoginFormEvent.PasswordChanged(it))
                },
                isError = state.passwordError != null,
                label = {
                    LocalText(R.string.password)
                },
                visualTransformation = if (state.showPassword)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(LoginFormEvent.ShowPassword(!state.showPassword))
                        }
                    ) {
                        if (state.showPassword) {
                            Icon(
                                Icons.Outlined.VisibilityOff,
                                ResText(R.string.hide_password).getText()
                            )
                        } else {
                            Icon(
                                Icons.Outlined.Visibility,
                                ResText(R.string.show_password).getText()
                            )
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Row(Modifier.padding(start = 16.dp, top = 4.dp)) {
                Text(
                    text = state.passwordError?.getText() ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
                TextButton(
                    onClick = {
                        viewModel.onEvent(LoginFormEvent.Restore(context))
                    }
                ) {
                    LocalText(
                        resId = R.string.forgot_password
                    )
                }
            }
            Spacer(Modifier.weight(3f))
            Text(
                text = state.serverError?.getText() ?: "",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 16.dp)
            )
            LinearProgressIndicator(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .alpha(if (state.isLoading) 1f else 0f)
            )
            Spacer(Modifier.weight(3f))
            Row {
                FilledTonalButton(
                    onClick = {
                        viewModel.onEvent(LoginFormEvent.Signup(context))
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    LocalText(R.string.signup)
                }
                Spacer(Modifier.width(16.dp))
                Button(
                    onClick = {
                        viewModel.onEvent(LoginFormEvent.Login)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    LocalText(R.string.login)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DONKidsTheme {
        LoginScreen(object : LoginViewModel() {})
    }
}
