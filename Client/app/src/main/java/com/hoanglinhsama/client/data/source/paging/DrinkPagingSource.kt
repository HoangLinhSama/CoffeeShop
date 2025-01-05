package com.hoanglinhsama.client.data.source.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hoanglinhsama.client.data.mapper.toDrinkDomain
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import com.hoanglinhsama.client.domain.model.Drink

class DrinkPagingSource(private val mainApi: MainApi) : PagingSource<Int, Drink>() {
    override fun getRefreshKey(state: PagingState<Int, Drink>): Int? {
        return state.anchorPosition?.let { it ->
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Drink> {
        val page = params.key ?: 1
        try {
            val response = mainApi.getDrink(page, params.loadSize)
            if (response.isSuccessful) {
                if (response.body()?.status == "success") {
                    val listDrink =
                        response.body()!!.result.map { it.toDrinkDomain() }
                    return LoadResult.Page(
                        listDrink,
                        if (page == 1) null else page - 1,
                        if (response.body()?.result?.isEmpty() == true) null else page + 1
                    )
                } else if (response.body()?.status == "fail: no data found") {
                    throw Exception("fail: no data found")
                } else if (response.body()?.status == "fail: no more data") {
                    Log.d(
                        "HLSM",
                        response.body()?.status.toString() + response.body()?.result.toString()
                    )
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