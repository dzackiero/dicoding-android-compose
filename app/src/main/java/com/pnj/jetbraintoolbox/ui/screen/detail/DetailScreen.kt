package com.pnj.jetbraintoolbox.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pnj.jetbraintoolbox.di.Injection
import com.pnj.jetbraintoolbox.ui.ViewModelFactory
import com.pnj.jetbraintoolbox.ui.common.UiState

@Composable
fun DetailScreen(
    name: String,
    viewModel: DetailViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getToolByName(name)
            }

            is UiState.Error -> {}
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    name = data.name,
                    image = data.imageResource,
                    description = data.description
                )
            }
        }
    }
}

@Composable
fun DetailContent(
    name: String,
    @DrawableRes image: Int,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "$name Icon",
            modifier = Modifier
                .fillMaxWidth()
                .size(240.dp),
        )
        Text(
            text = name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
        )
        Spacer(Modifier.size(8.dp))
        Text(
            text = description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Justify,
        )
    }
}