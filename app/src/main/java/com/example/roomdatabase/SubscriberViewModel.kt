package com.example.roomdatabase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.db.Subscriber
import com.example.roomdatabase.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository): ViewModel() {

    val subscribers = repository.subscribers

     val inputname = MutableLiveData<String>()
     val inputemail = MutableLiveData<String>()

    val saveorupdatebutton = MutableLiveData<String>()
    val clearallordeletebutton = MutableLiveData<String>()

    init {
        saveorupdatebutton.value = "Save"
        clearallordeletebutton.value = "Clear All"
    }

    fun saveorupdate(){
        val name = inputname.value!!
        val email = inputemail.value!!
        insert(Subscriber(0,name,email))
        inputname.value = ""
        inputemail.value = ""
        saveorupdatebutton.value = "Save"
        clearallordeletebutton.value = "Clear All"
    }

    fun clearallordelete(){
        clearAll()
    }

    fun insert(subscriber: Subscriber) = viewModelScope.launch(IO) {
        repository.insertSubscriber(subscriber)
    }

    fun update(subscriber: Subscriber) = viewModelScope.launch(IO) {
        repository.updateSubscriber(subscriber)
    }

    fun delete(subscriber: Subscriber) = viewModelScope.launch(IO) {
        repository.deleteSubscriber(subscriber)
    }

    fun clearAll() = viewModelScope.launch(IO) {
        repository.deleteAll()
    }
}