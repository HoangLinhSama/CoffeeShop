package com.hoanglinhsama.client.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hoanglinhsama.client.data.mapper.toPolicyDomain
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import com.hoanglinhsama.client.domain.model.Policies

class PolicyPagingSource(private val mainApi: MainApi) : PagingSource<Int, Policies>() {
    override fun getRefreshKey(state: PagingState<Int, Policies>): Int? {
        return state.anchorPosition?.let { it ->
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Policies> {
        val page = params.key ?: 1
        try {
            val response = mainApi.getPolicy(page, params.loadSize)
            if (response.isSuccessful) {
                if (response.body()?.status == "success") {
                    val listPolicy: List<Policies>? =
                        response.body()?.result?.map { it.toPolicyDomain() }
                    return LoadResult.Page(
                        listPolicy!!,
                        if (page == 1) null else page - 1,
                        if (response.body()?.result?.isEmpty() == true) null else page + 1
                    )
                } else if (response.body()?.status == "fail: no more data") {
                    return LoadResult.Page(emptyList(), null, null)
                } else if (response.body()?.status == "fail: no data found") {
                    return LoadResult.Error(Exception("${response.body()?.status}"))
                } else {
                    return LoadResult.Error(Exception("${response.body()?.status}"))
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