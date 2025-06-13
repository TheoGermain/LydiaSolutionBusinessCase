package com.example.lydia_solution_business_case.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.lydia_solution_business_case.data.api.ContactApi
import com.example.lydia_solution_business_case.data.db.ContactEntity
import com.example.lydia_solution_business_case.data.db.LocalDatabase
import com.example.lydia_solution_business_case.data.remote.model.toEntity

@OptIn(ExperimentalPagingApi::class)
class ContactRemoteMediator(
    private val database: LocalDatabase,
    private val api: ContactApi,
) : RemoteMediator<Int, ContactEntity>() {

    override suspend fun initialize(): InitializeAction {
        return if (database.contactDao().countItems() > 0) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ContactEntity>,
    ): MediatorResult {
        val dao = database.contactDao()

        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> (dao.getMaxPage() ?: 0) + 1
        }
        return try {
            val response = api.getContacts(page = page)
            if (response.info.page != page) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            val contacts = response.results.mapIndexed { index, dto -> dto.toEntity(page, index) }

            database.withTransaction {
                if (loadType == LoadType.REFRESH && dao.countItems() > 0) {
                    dao.clearAll()
                }
                dao.insertAll(contacts)
            }

            MediatorResult.Success(endOfPaginationReached = false)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}