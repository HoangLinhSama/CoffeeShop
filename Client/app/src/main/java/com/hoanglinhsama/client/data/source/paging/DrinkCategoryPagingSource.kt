package com.hoanglinhsama.client.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hoanglinhsama.client.data.mapper.toDrinkCategoryDomain
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import com.hoanglinhsama.client.domain.model.DrinkCategory
import java.lang.Exception

class DrinkCategoryPagingSource(private val mainApi: MainApi) :
    PagingSource<Int, DrinkCategory>() {
    override fun getRefreshKey(state: PagingState<Int, DrinkCategory>): Int? {
        return state.anchorPosition?.let { it ->
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DrinkCategory> {
        val page = params.key ?: 1
        try {
            val response = mainApi.getDrinkCategory(page, params.loadSize)
            if (response.isSuccessful) {
                if (response.body()?.status == "success") {
                    val listDrinkCategory =
                        response.body()!!.result.map { it.toDrinkCategoryDomain() }
                    return LoadResult.Page(
                        listDrinkCategory,
                        if (page == 1) null else page - 1,
                        if (response.body()?.result?.isEmpty() == true) null else page + 1
                    )
                } else if (response.body()?.status == "fail: no data found") {
                    throw Exception("fail: no data found")
                } else if (response.body()?.status == "fail: no more data") {
                    return LoadResult.Page(emptyList(), null, null)
                } else {
                    return LoadResult.Error(Exception("Failure: ${response.body()?.status}"))
                }
            } else {
                return LoadResult.Error(Exception(("API request failed with status: ${response.code()}")))
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }
}