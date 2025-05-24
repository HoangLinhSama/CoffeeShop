package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.data.model.UniqueResult
import com.hoanglinhsama.client.domain.model.DrinkOrder
import com.hoanglinhsama.client.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateTempOrderUseCase @Inject constructor(private val mainRepository: MainRepository) {
    operator fun invoke(
        id: Int,
        picture: String,
        name: String,
        size: String?,
        listTopping: List<String>?,
        noteOrder: String,
        countDrink: Int,
        totalPrice: Float,
        drinkCategory: String,
    ): Flow<UniqueResult<DrinkOrder>> {
        return mainRepository.createTempOrder(
            id,
            picture,
            name,
            size,
            listTopping,
            noteOrder,
            countDrink,
            totalPrice,
            drinkCategory
        )
    }
}