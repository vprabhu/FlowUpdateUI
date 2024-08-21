package com.vpdevs.flowupdateui.ui.screens

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vpdevs.flowupdateui.UiState
import com.vpdevs.flowupdateui.ui.theme.FlowUpdateUITheme
import com.vpdevs.flowupdateui.viewmodel.CalculationViewmodel

@Composable
fun HomeScreen(
    viewmodel: CalculationViewmodel = viewModel()
) {

    val result by viewmodel.result.collectAsState()

    val loadingStatus = remember { mutableStateOf(false) }

    val resultText = remember {
        mutableStateOf("")
    }

    render(result, resultText, loadingStatus)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        Button(
            onClick = {
                resultText.value = ""
                viewmodel.getResult()
                loadingStatus.value = true
                Log.d("HomeScreen", "onClick ${loadingStatus.value}")
            }
        ) {
            Text(text = "Get Result with 2.5 sec delay")
        }

        ShowProgress(loading = loadingStatus)

        Text(
            text = resultText.value,
            style = MaterialTheme.typography.displayLarge
        )

    }

}

@Composable
private fun ShowProgress(
    loading: MutableState<Boolean>
) {
    AnimatedVisibility(visible = loading.value) {

        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

private fun render(
    uiState: UiState,
    resultText: MutableState<String>,
    loading: MutableState<Boolean>
) {
    when (uiState) {
        is UiState.Loading -> {
            loading.value = true
        }

        is UiState.Success -> {
            resultText.value = uiState.stockList
            loading.value = false
            Log.d("HomeScreen", "UiState.Success ${loading.value}")
        }

        is UiState.Error -> {
            loading.value = false
            Log.d("HomeScreen", "UiState.Error")
        }

        UiState.InitValue -> {

        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenPreview() {
    FlowUpdateUITheme {
        HomeScreen()
    }
}