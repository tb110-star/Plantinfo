package org.example.project.ui.screens

import AddImageSheetScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.ui.components.PlantCard
import org.example.project.ui.viewModels.PlantInfoViewModel
import org.example.project.ui.viewModels.UploadImageViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun PlantInfoScreen(
    viewModel: PlantInfoViewModel = koinViewModel<PlantInfoViewModel>()
){
    val uploadImageViewModel: UploadImageViewModel = koinViewModel()

    val isShowingAddSheet by viewModel.isShowingAddSheet.collectAsState()
    val isDone by viewModel.isDone.collectAsState()
val plantInfo = viewModel.plantInfo.collectAsState()
    val suggestions = plantInfo.value?.result?.classification?.suggestions ?: emptyList()
    Scaffold (

        floatingActionButton = {
            FloatingActionButton (onClick = viewModel::enableAddSheet,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ){
                Icon(Icons.Default.Camera,"Upload Image")
            }
        }

    ){ innerPadding ->
        Column ( modifier = Modifier.padding(innerPadding)){

            if (plantInfo != null) {
                val topSuggestions = suggestions
                    .sortedByDescending { it.probability }
                    .take(5)

                LazyColumn {
                    items(topSuggestions) { suggestion ->
                        PlantCard(plant = suggestion) {
                            // navigate to detail screen with this suggestion
                            //  "detail/{id}"
                            // navController.navigate("detail/${suggestion.id}")
                        }
                    }
                }


            } else {
                Text(
                    text = "No plant info loaded yet.",
                    modifier = Modifier.padding(8.dp)
                )
            }

        }

        if(isShowingAddSheet) {

            ModalBottomSheet(
                onDismissRequest = viewModel :: disableAddSheet
            ) {
                AddImageSheetScreen(

                    onCloseClick = viewModel::disableAddSheet,

                )


            }
        }
    }

}