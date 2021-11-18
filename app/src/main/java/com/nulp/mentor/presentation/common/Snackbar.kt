package com.nulp.mentor.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nulp.mentor.presentation.theme.ErrorBackground
import com.nulp.mentor.presentation.theme.errorTypo
import kotlinx.coroutines.delay


@Composable
fun SnackbarError(text: String) {
    var show by remember {
        mutableStateOf(true)
    }
    if(show) {
        Snackbar(
            backgroundColor= ErrorBackground,
            modifier = Modifier
                .padding(8.dp)
        ) { Text(text = text, style = errorTypo) }
    }
    LaunchedEffect(key1 = 3, block = {
        delay(3000)
        show = false
    })
}

