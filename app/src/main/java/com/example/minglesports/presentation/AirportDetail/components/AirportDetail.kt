package com.example.minglesports.presentation.AirportDetail.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun BottomSheetScaffoldSample() {
    val scope = rememberCoroutineScope()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            // BottomSheet content
        },
        sheetPeekHeight = 56.dp
    ) {
        // Content behind BottomSheet
    }
}