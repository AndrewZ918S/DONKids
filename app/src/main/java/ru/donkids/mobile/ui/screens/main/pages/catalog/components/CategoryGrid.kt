package ru.donkids.mobile.ui.screens.main.pages.catalog.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.donkids.mobile.domain.model.Product

@Composable
fun CategoryGrid(
    categories: List<Product>,
    onCategory: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        items(categories.size) { index ->
            ItemCategory(
                category = categories[index],
                onClick = {
                    onCategory(it)
                }
            )
        }
    }
}