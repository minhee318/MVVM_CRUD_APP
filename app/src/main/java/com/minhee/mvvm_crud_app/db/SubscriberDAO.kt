package com.minhee.mvvm_crud_app.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriberDAO {
    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber) : Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber) : Int

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber) : Int

    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscribers(): Flow<List<Subscriber>>
    //ViewModel내에서 Flow를 LiveData로 쉽게 변환할 수 있다.
    //LiveData는 생명주기가 필요하므로 저장소 내부 또는 클래스 아래에서 사용하면 오류 발생
}