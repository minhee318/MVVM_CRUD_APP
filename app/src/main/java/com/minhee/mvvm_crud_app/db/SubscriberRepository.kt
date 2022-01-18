package com.minhee.mvvm_crud_app.db

/**
여러 데이터 소스를 관리하는 데 주로 사용된다.
ViewModel은 repository에서 데이터를 요청하거나 데이터를 보낸다.
로컬 데이터베이스 또는 원격 API를 처리한다.

데이터 변경사항을 한 곳에 집중
여러 데이터 소스 간의 충돌 해결
여러 데이터 소스 액세스를 추상화

앱에서 처리하는 다양한 유형의 데이터마다 repository를 만들어야한다.
ex.영화 관련 MovieRepository, 결제 관련 PaymentRepository
 **/

class SubscriberRepository(private val dao: SubscriberDAO) {
    //SubscriberDAO 인스턴스 필요 => 생성자 매개변수로 추가

    val subscribers = dao.getAllSubscribers()
    //Flow를 반환하기 때문에 suspend 필요없다.


    //메인스레드에서 room db접근 못함 ---> 코루틴 이용
    suspend fun insert(subscriber: Subscriber): Long {
        return dao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber): Int {
        return dao.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber): Int {
        return dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }
}