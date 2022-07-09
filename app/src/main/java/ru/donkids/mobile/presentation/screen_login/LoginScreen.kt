package ru.donkids.mobile.presentation.screen_login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.donkids.mobile.R
import ru.donkids.mobile.presentation.Destinations
import ru.donkids.mobile.presentation.components.openCustomTab
import ru.donkids.mobile.presentation.ui.theme.DONKidsTheme
import ru.donkids.mobile.presentation.ui.theme.SystemBarColor

@Composable
fun LoginScreen(navController: NavController? = null) {
    val viewModel: LoginScreenViewModel = when (LocalView.current.isInEditMode) {
        true -> object : LoginScreenViewModel() {}
        false -> hiltViewModel<LoginScreenScreenViewModelImpl>()
    }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is LoginScreenViewModel.Event.Proceed -> {
                    navController?.navigate(Destinations.MAIN)
                }
                is LoginScreenViewModel.Event.OpenUrl -> {
                    openCustomTab(context, event.url)
                }
            }
        }
    }

    SystemBarColor(
        statusBarColor = colorScheme.surface,
        navigationBarColor = colorScheme.surface
    )

    Surface {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = !WindowInsets.isImeVisible || LocalView.current.isInEditMode
            ) {
                Column {
                    Spacer(Modifier.height(32.dp))
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        tint = colorScheme.onSurface,
                        contentDescription = null,
                        modifier = Modifier
                            .size(96.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(Modifier.height(32.dp))
                    Text(
                        text = stringResource(R.string.auth),
                        color = colorScheme.onSurface,
                        style = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 36.sp
                        )
                    )
                    Spacer(Modifier.height(48.dp))
                }
            }
            OutlinedTextField(
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(LoginScreenEvent.EmailChanged(it))
                },
                isError = state.emailError != null,
                label = {
                    Text(stringResource(R.string.email))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = state.emailError ?: "",
                color = colorScheme.error,
                style = typography.bodySmall,
                modifier = Modifier
                    .padding(start = 16.dp, top = 4.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(LoginScreenEvent.PasswordChanged(it))
                },
                isError = state.passwordError != null,
                label = {
                    Text(stringResource(R.string.password))
                },
                visualTransformation = if (state.showPassword)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Go
                ),
                keyboardActions = KeyboardActions(
                    onGo = {
                        viewModel.onEvent(LoginScreenEvent.Login)
                    }
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(LoginScreenEvent.ShowPassword(!state.showPassword))
                        }
                    ) {
                        Icon(
                            painterResource(
                                if (state.showPassword)
                                    R.drawable.ic_visibility_off
                                else
                                    R.drawable.ic_visibility
                            ),
                            stringResource(R.string.hide_password)
                        )
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Row(Modifier.padding(start = 16.dp, top = 4.dp)) {
                Text(
                    text = state.passwordError ?: "",
                    color = colorScheme.error,
                    style = typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
                TextButton(
                    onClick = {
                        viewModel.onEvent(LoginScreenEvent.Restore)
                    }
                ) {
                    Text(stringResource(R.string.forgot_password))
                }
            }
            Spacer(Modifier.weight(1f))
            Text(
                text = state.serverError ?: "",
                color = colorScheme.error,
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
            Spacer(Modifier.weight(1f))
            Row {
                FilledTonalButton(
                    onClick = {
                        viewModel.onEvent(LoginScreenEvent.Signup)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.signup))
                }
                Spacer(Modifier.width(16.dp))
                Button(
                    onClick = {
                        viewModel.onEvent(LoginScreenEvent.Login)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.login))
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    DONKidsTheme {
        LoginScreen()
    }
}
