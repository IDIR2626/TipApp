package com.example.tipapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipapp.components.InputField
import com.example.tipapp.util.calculateTotalPerPerson
import com.example.tipapp.widgets.RoundedIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()) {
                MainContent()
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun TopHeader(totalPerPerson : Double = 0.0){

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(12.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(8.dp))),
        tonalElevation = 10.dp,
        shadowElevation = 10.dp,
        color = Color(0xFFD0BCFF)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Total per person",
                style = MaterialTheme.typography.headlineMedium,
            )

            val total = String.format("%.2f", totalPerPerson)
            Text(

                text = "$$total",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.SemiBold
            )
        }

    }
}

@SuppressLint("RememberReturnType")
@Preview(showBackground = true)
@Composable
fun MainContent(){
    BillForm()
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    onValueChange : (String) -> Unit = {}
){

    val totalBillState = remember {
        mutableStateOf("")
    }
    val validInputState = remember (totalBillState.value){
        totalBillState.value.trim().isNotEmpty()
    }

    val splitValueState = remember {
        mutableStateOf(1)
    }

    val validSplitValueStat = remember (splitValueState.value) {
        splitValueState.value > 1
    }

    val sliderPositionState = remember {
        mutableStateOf(0f)
    }

    val tipPercentage = (sliderPositionState.value * 100).toInt()

    val tipValue =  remember {
        mutableStateOf(0.0)
    }

    val totalPerPerson = remember {
        mutableStateOf(0.00)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    TopHeader(totalPerPerson.value)

    Surface(
        Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(
                shape = RoundedCornerShape(corner = CornerSize(8.dp))
            )
            .border(width = 2.dp, color = Color(0xFFD0BCFF))





    ) {
        Column(
            Modifier.padding(5.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            InputField(
                valueState = totalBillState,
                labelId = "Inter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions{
                    if (!validInputState) return@KeyboardActions
                    onValueChange(totalBillState.value.trim())
                    keyboardController?.hide()

                },

                )

            if (validInputState){

                // The Split Row
                Row(
                    modifier.padding(3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Split",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(Modifier.width(120.dp))
                    Row(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        RoundedIconButton(imageVector = R.drawable.remove_btn){
                            //decrease the split value
                            if (validSplitValueStat) {
                                splitValueState.value = splitValueState.value - 1
                                totalPerPerson.value = calculateTotalPerPerson(totalBillState.value.toDouble(), splitValueState.value.toInt(), tipValue.value.toInt())

                            }
                        }

                        Text(
                            text = splitValueState.value.toString(),
                            Modifier
                                .align(alignment = Alignment.CenterVertically)
                                .padding(start = 9.dp, end = 9.dp),
                            style = MaterialTheme.typography.titleMedium
                        )

                        RoundedIconButton(imageVector = R.drawable.add_btn){
                            // increase the split value
                            splitValueState.value = splitValueState.value +1
                            totalPerPerson.value = calculateTotalPerPerson(totalBillState.value.toDouble(), splitValueState.value.toInt(), tipValue.value.toInt())

                        }
                    }
                }
                

                // The Tip Row
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 3.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Tip",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.width(150.dp))

                    Text(
                        modifier = Modifier.align(alignment = Alignment.CenterVertically),
                        text = "${tipValue.value.toInt()}$",
                        style = MaterialTheme.typography.titleMedium
                    )


                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp, horizontal = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "$tipPercentage%",
                        style = MaterialTheme.typography.titleMedium
                    )

                    //Slider
                    Slider(
                        value = sliderPositionState.value,
                        onValueChange = {newVal ->
                        sliderPositionState.value = newVal
                            tipValue.value =  (tipPercentage * totalBillState.value.toDouble())/100
                            totalPerPerson.value = calculateTotalPerPerson(totalBillState.value.toDouble(), splitValueState.value.toInt(), tipValue.value.toInt())


                        },
                        colors = SliderDefaults.colors(
                            activeTickColor = Color(0xFFD0BCFF),
                            inactiveTickColor = Color(0xFFD0BCFF),
                            thumbColor = Color(0xFF6650a4),
                            activeTrackColor = Color(0xFFD0BCFF)
                        )
                    )
                }

                totalPerPerson.value = calculateTotalPerPerson(totalBillState.value.toDouble(), splitValueState.value.toInt(), tipValue.value.toInt())

            }
        }

    }

}