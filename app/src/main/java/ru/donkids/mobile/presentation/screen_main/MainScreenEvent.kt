package ru.donkids.mobile.presentation.screen_main

sealed class MainScreenEvent {
    data class NavigationItemSelected(val index: Int) : MainScreenEvent()
    object CheckLogin : MainScreenEvent()
}
