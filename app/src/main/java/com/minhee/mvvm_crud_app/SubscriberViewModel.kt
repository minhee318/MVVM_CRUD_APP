package com.minhee.mvvm_crud_app

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minhee.mvvm_crud_app.db.Subscriber
import com.minhee.mvvm_crud_app.db.SubscriberRepository
import kotlinx.coroutines.launch


class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {

    //양방향 데이터 바인딩을 구현하려면 뷰 모델 변수를 MutatbleLiveData로 정의
    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()


    //초기값 : 저장/모두 지우기
    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        //입력필드에 삽입된 이름과 이메일 가져오기
        val name = inputName.value!!
        val email = inputEmail.value!!
        insertSubscriber(Subscriber(0, name, email))
        //입력값 지우기
        inputName.value = ""
        inputEmail.value = ""
    }

    private fun insertSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        repository.insert(subscriber)
    }

}