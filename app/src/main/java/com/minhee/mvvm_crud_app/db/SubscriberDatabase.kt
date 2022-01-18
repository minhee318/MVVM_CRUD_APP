package com.minhee.mvvm_crud_app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
여러 엔터티(model)를 사용하는 경우
entities = [RoomTodoData::class, OtherData::class] <- 추가합니다.

abstract fun TodoDao(): RetrofitTodoData
abstract fun otherDao(): OtherData <- 추가합니다.

앱이 깔린 상태에서 엔터티(model)를 수정한 경우 : 앱을 다시 깔거나 버전을 올려줍니다.
 **/

@Database(entities = [Subscriber::class], version = 1)
abstract class SubscriberDatabase : RoomDatabase() {
    abstract val subscriberDAO: SubscriberDAO

    //데이터베이스 인스턴스를 싱글톤으로 사용하기 위해
    companion object {
        @Volatile
        private var INSTANCE: SubscriberDatabase? = null

        //MainActivity에서 호출하여 database 객체를 반환할 수 있도록 getInstance() 메소드를 생성해준다.
        fun getInstance(context: Context): SubscriberDatabase {
            synchronized(this) {
                //getInstance함수를 여러 스레드가 접근하지 못하도록
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SubscriberDatabase::class.java,
                        "subscriber_data_database"
                         //데이터베이스 이름 지정
                    ).build()
                }
                return instance
            }
        }
    }
}