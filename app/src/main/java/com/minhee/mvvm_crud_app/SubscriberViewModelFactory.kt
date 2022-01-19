package com.minhee.mvvm_crud_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.minhee.mvvm_crud_app.db.SubscriberRepository

/**

뷰 모델 팩토리가 필요한 이유 ?
    ViewModelProvider.Factory를 구현하면 파라미터를 소유하고 있는 ViewModel 객체의 인스턴스를 생성할 수 있습니다.
    직접 구현한 Factory 클래스에 파라미터를 넘겨주어 create() 내에서 인스턴스를 생성할 때 활용하면 됩니다.

viewModels와 ViewModelProvider.Factory를 사용하여 프레임워크에서 ViewModel의 수명 주기를 처리합니다.
구성 변경에도 유지되고 Activity가 다시 생성되더라도 항상 SubscriberViewModel 클래스의 올바른 인스턴스를 가져오게 됩니다.

생성자 매개변수를 결정하는 방법은 ?
    ViewModel클래스에는 생성자 매개변수로 repository 인스턴스가 있다. 따라서 Factory클래스도 동일한 생성자 매개변수를 가져야 한다.

 **/

class SubscriberViewModelFactory(private val repository: SubscriberRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SubscriberViewModel::class.java)){
            //modelClass가 SubscriberViewModel클래스를 상속했는지 확인
            return SubscriberViewModel(repository) as T
            //SubscriberViewModelFactory의 생성자로 repository를 받아 ViewModel 인스턴스 생성 시 전달해줍니다.
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}


