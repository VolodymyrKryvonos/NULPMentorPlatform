package com.nulp.mentor.presentation.home_screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nulp.mentor.presentation.home_screen.components.BestMentorsListItem

@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val bestMentorsState = homeViewModel.mentorsState.value
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Найкращі ментори",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 28.dp).align(Alignment.CenterHorizontally)
        )

        if (bestMentorsState.mentors.isNotEmpty()) {
            LazyRow(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 15.dp)) {
                items(bestMentorsState.mentors) { bestMentor ->
                    BestMentorsListItem(
                        bestMentor = bestMentor,
                        onItemClick = { Log.e("BestMentorClick", bestMentor.mentor.email) })
                }
            }
        }
    }

}