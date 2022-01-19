package com.minhee.mvvm_crud_app

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.minhee.mvvm_crud_app.db.Subscriber
import com.minhee.mvvm_crud_app.db.SubscriberRepository
import kotlinx.coroutines.launch

/**
    AAC의 ViewModel 추상클래스를 상속해서 정의한 ViewModel 클래스는 개발자가 직접 생성자 메서드로
    인스턴스 생성이 불가능합니다. ViewModelProvider를 사용해서 인스턴스를 생성할 수 밖에 없습니다

    Factory클래스는 Provider에서 ViewModel 인스턴스 생성에 필요로 합니다

    repository를 통해 model과 소통 ==> repository 클래스의 인스턴스를 생성자 매개변수로 추가
    ViewModel 및 LiveData를 이용한 양방향 데이터 바인 => ViewModel의 값에서 xml레이아웃 속성 값을 자동으로 업데이트

    수정가능 : MutatbleLiveData

 **/

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    //양방향 데이터 바인딩을 구현하려면 뷰 모델 변수를 MutatbleLiveData로 정의
    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>() //캡슐화
    val message: LiveData<Event<String>> //statusMessage 공유를 위한 message변수
        get() = statusMessage


    //초기값 : 저장/모두 지우기
    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        if (inputName.value == null) {
            statusMessage.value = Event("Please enter subscriber's name")
        } else if (inputEmail.value == null) {
            statusMessage.value = Event("Please enter subscriber's email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("Please enter a correct email address")
        } else {
            if (isUpdateOrDelete) {
                subscriberToUpdateOrDelete.name = inputName.value!!
                subscriberToUpdateOrDelete.email = inputEmail.value!!
                updateSubscriber(subscriberToUpdateOrDelete)
            } else {
                //입력필드에 삽입된 이름과 이메일 가져오기
                val name = inputName.value!!
                val email = inputEmail.value!!
                insertSubscriber(Subscriber(0, name, email))
                //입력값 지우기
                inputName.value = ""
                inputEmail.value = ""
            }
        }
    }

    private fun insertSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        val newRowId = repository.insert(subscriber)
        if (newRowId > -1) {
            statusMessage.value = Event("Subscriber Inserted Successfully $newRowId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    //저장된 데이터 목록을 가져오는 함수
    fun getSavedSubscribers() = liveData {
        repository.subscribers.collect {
            emit(it)
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            deleteSubscriber(subscriberToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    private fun deleteSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(subscriber)
        if (noOfRowsDeleted > 0) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRowsDeleted Row Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }


    private fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("$noOfRowsDeleted Subscribers Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun initUpdateAndDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    private fun updateSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        val noOfRows = repository.update(subscriber)
        if (noOfRows > 0) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRows Row Updated Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }

    }
}