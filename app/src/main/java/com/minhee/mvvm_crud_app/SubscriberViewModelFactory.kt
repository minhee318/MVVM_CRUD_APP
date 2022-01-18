package com.minhee.mvvm_crud_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.minhee.mvvm_crud_app.db.SubscriberRepository

/**

뷰 모델 팩토리가 필요한 이유 ?
    ViewModel클래스에는 생성자 매개변수가 있다. 따라서 인스턴스를 구성하려면 Factory클래스의 지원을 받아야 한다.

생성자 매개변수를 결정하는 방법은 ?
    ViewModel클래스에는 생성자 매개변수로 저장소 인스턴스가 있다. 따라서 Factory클래스도 동일한 생성자 매개변수를 가져야 한다.

 **/

class SubscriberViewModelFactory(private val repository: SubscriberRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SubscriberViewModel::class.java)){
            return SubscriberViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}


