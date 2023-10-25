package com.pnj.jetbraintoolbox.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pnj.jetbraintoolbox.di.Injection
import com.pnj.jetbraintoolbox.R
import com.pnj.jetbraintoolbox.model.JetbrainsTool
import com.pnj.jetbraintoolbox.ui.ViewModelFactory
import com.pnj.jetbraintoolbox.ui.common.UiState
import com.pnj.jetbraintoolbox.ui.component.ListToolItem
import com.pnj.jetbraintoolbox.ui.theme.JetbrainToolboxTheme


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (String) -> Unit,
) {
    Box(
        modifier.fillMaxSize()
    ) {
        viewModel.uiState.collectAsState(UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    viewModel.getAllTools()
                }

                is UiState.Error -> {}

                is UiState.Success -> {
                    val toolList = uiState.data
                    HomeContent(
                        tools = toolList,
                        navigateToDetail = navigateToDetail,
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}

@Composable
fun HomeContent(
    tools: List<JetbrainsTool>,
    viewModel: HomeViewModel,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val query by viewModel.query

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            MySearchBar(
                query = query,
                onQueryChange = viewModel::search,
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            )
        }

        items(
            items = tools,
            key = { it.name }
        ) { tool ->
            ListToolItem(
                tool.name,
                tool.imageResource,
                navigateToDetail,
                modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text((stringResource(R.string.search)))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ) {
    }
}

@Preview
@Composable
fun HomePreview() {
    JetbrainToolboxTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
        }
    }
}