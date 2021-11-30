package com.nulp.mentor.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nulp.mentor.R
import com.nulp.mentor.domain.model.Subject
import com.nulp.mentor.presentation.theme.ColorPrimary
import com.nulp.mentor.presentation.theme.h3

@ExperimentalMaterialApi
@Composable
fun SubjectListItem(
    subject: Subject,
    onItemClick: (Subject) -> Unit = {}
) {
    Surface(
        shape = RoundedCornerShape(10),
        modifier = Modifier
            .width(125.dp)
            .height(140.dp)
            .padding(5.dp),
        onClick = { onItemClick(subject) }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(ColorPrimary)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_computer),
                contentDescription = "subject item",
                modifier = Modifier
                    .size(70.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                tint = Color.White
            )
            Text(
                text = "${subject.name}",
                style = h3,
                fontSize = 13.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 10.dp),
            )
        }
    }
}