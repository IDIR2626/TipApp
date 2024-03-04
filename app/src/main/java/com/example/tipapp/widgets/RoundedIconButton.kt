package com.example.tipapp.widgets

import android.view.RoundedCorner
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val IconSizeButtonModifier = Modifier.size(40.dp)

@Composable
fun RoundedIconButton(
    modifier: Modifier = Modifier,
    imageVector: Int,
    tint: Color = Color.Black,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    elevation: Dp = 2.dp,
    onClick: () -> Unit,

    ){
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick.invoke() }
            .then(IconSizeButtonModifier)
            ,
        elevation = CardDefaults.cardElevation(elevation),
        colors = CardDefaults.cardColors(backgroundColor),
        shape = AbsoluteRoundedCornerShape(100)

    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ){
            Icon(painter = painterResource(id = imageVector), contentDescription = "Add or Remove button", tint = tint)
        }

    }
}