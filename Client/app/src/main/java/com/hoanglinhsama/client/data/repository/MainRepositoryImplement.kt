package com.hoanglinhsama.client.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hoanglinhsama.client.data.source.paging.DrinkCategoryPagingSource
import com.hoanglinhsama.client.data.source.paging.DrinkPagingSource
import com.hoanglinhsama.client.data.source.paging.VoucherPagingSource
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.model.DrinkCategory
import com.hoanglinhsama.client.domain.model.Voucher
import com.hoanglinhsama.client.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryImplement @Inject constructor(private val mainApi: MainApi) : MainRepository {
    override fun getPromotion(): Flow<PagingData<Voucher>> {
        return Pager(config = PagingConfig(initialLoadSize = 6, pageSize = 6),
            pagingSourceFactory = {
                VoucherPagingSource(mainApi = mainApi)
            }).flow
    }

    override fun getDrinkCategory(): Flow<PagingData<DrinkCategory>> {
        return Pager(config = PagingConfig(initialLoadSize = 3, pageSize = 3),
            pagingSourceFactory = {
                DrinkCategoryPagingSource(mainApi = mainApi)
            }).flow
    }

    override fun getDrink(): Flow<PagingData<Drink>> {
        return Pager(
            config = PagingConfig(initialLoadSize = 10, pageSize = 10),
            pagingSourceFactory = {
                DrinkPagingSource(mainApi = mainApi)
            }).flow
    }

}