package com.nulp.mentor.presentation

sealed class Screen(val route: String) {
    object AddSubjectScreen : Screen("add_subject")
    object LoginScreenNavigation : Screen("login_screen")
    object RegisterScreenNavigation: Screen("register_screen")
    object HomeScreen: Screen("home_screen")
    object NotificationScreen: Screen("notification_screen")
    object AccountScreen : Screen("account_screen")
    object FindMentorScreen : Screen("find_mentor")
}