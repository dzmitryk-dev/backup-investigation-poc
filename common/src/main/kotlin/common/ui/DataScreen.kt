package common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.domain.DataState

@Composable
fun DataScreen(
    readDataState: State<DataState>,
    generatedData: State<DataState>,
    reReadData: () -> Unit,
    generateAndSaveFunction: (Int) -> Unit,
    removeData: () -> Unit,
) {

    var length by rememberSaveable { mutableStateOf("12") }

    Column(modifier = Modifier) {
        Text(text = "Retrieved data", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        DataField(readDataState.value)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                reReadData()
            },
        ) { Text("Re-read") }

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Generated data.", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        DataField(generatedData.value)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = length,
            onValueChange = { length = it },
            label = { Text("Lenght:") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                generateAndSaveFunction(length.toInt())
            },
        ) { Text("Save") }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { removeData() },
        ) { Text("Remove") }
    }
}

@Composable
private fun DataField(dataState: DataState) {
    when (dataState) {
        DataState.LOADING -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                LinearProgressIndicator()
            }
        }
        DataState.EMPTY -> Text(text = "No data available.", fontSize = 24.sp)
        is DataState.Data -> Text(text = "Data: ${dataState.value}", fontSize = 24.sp)
    }
}

@Preview
@Composable
private fun PreviewDataField() {
    DataField(DataState.LOADING)
}

@Preview
@Composable
private fun PreviewDataScreen() {
    val readDataState = remember { mutableStateOf(DataState.EMPTY) }
    val generatedDataState = remember { mutableStateOf(DataState.EMPTY) }

    DataScreen(
        readDataState,
        generatedDataState,
        {},
        {},
        {}
    )
}
