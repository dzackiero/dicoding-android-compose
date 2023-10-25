package com.pnj.jetbraintoolbox.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pnj.jetbraintoolbox.model.FakeJetbrainsAppDataSource
import com.pnj.jetbraintoolbox.ui.theme.JetbrainToolboxTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListToolItem(
    name: String,
    image: Int,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        onClick = { navigateToDetail(name) }
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "$name icon",
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListItem() {
    JetbrainToolboxTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            val tool = FakeJetbrainsAppDataSource.appList[0]
            ListToolItem(name = tool.name, image = tool.imageResource, {})
        }
    }
}