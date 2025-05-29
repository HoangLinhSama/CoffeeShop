package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.data.model.UniqueResult
import com.hoanglinhsama.client.domain.model.DrinkOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateTempOrderUseCase @Inject constructor() {
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
        return flow {
            emit(UniqueResult(result = Result.Loading))
            try {
                val order = DrinkOrder(
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
                emit(UniqueResult(result = Result.Success(order)))
            } catch (e: Exception) {
                emit(UniqueResult(result = Result.Error(e)))
            }
        }
    }
}