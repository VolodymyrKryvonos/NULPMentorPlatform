package com.nulp.mentor.presentation.home_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nulp.mentor.R
import com.nulp.mentor.domain.model.BestMentor
import com.nulp.mentor.presentation.theme.h3

@ExperimentalMaterialApi
@Composable
fun BestMentorsListItem(
    bestMentor: BestMentor,
    onItemClick: (BestMentor) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10),
        modifier = Modifier
            .width(185.dp)
            .height(240.dp)
            .padding(all = 5.dp),
        onClick = { onItemClick(bestMentor) },
        elevation = 5.dp
    ) {
        Column(Modifier.padding(10.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_user_icon),
                contentDescription = "mentor icon",
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 10.dp)
            )
            Text(
                text = "${bestMentor.mentor.name} ${bestMentor.mentor.surname}",
                style = h3,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            Text(
                text = bestMentor.subject.name,
                color = Color.DarkGray,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "${bestMentor.mentor.mentees.size} підопічний(их)",
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "%.1f".format(bestMentor.mentor.rate),
                color = Color.Yellow,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}