package com.example.roomdatabase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.db.Subscriber
import com.example.roomdatabase.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscriberViewModel(private val repository: SubscriberRepository): ViewModel() {

    val subscribers = repository.subscribers
    var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete : Subscriber

     val inputname = MutableLiveData<String>()
     val inputemail = MutableLiveData<String>()

    val saveorupdatebutton = MutableLiveData<String>()
    val clearallordeletebutton = MutableLiveData<String>()

    init {
        saveorupdatebutton.value = "Save"
        clearallordeletebutton.value = "Clear All"
    }

    fun saveorupdate(){
        if (isUpdateOrDelete){
            subscriberToUpdateOrDelete.name = inputname.value!!
            subscriberToUpdateOrDelete.email = inputemail.value!!
            update(subscriberToUpdateOrDelete)
            inputname.value = ""
            inputemail.value = ""
        }else{
            val name = inputname.value!!
            val email = inputemail.value!!
            insert(Subscriber(0,name,email))
            inputname.value = ""
            inputemail.value = ""
            saveorupdatebutton.value = "Save"
            clearallordeletebutton.value = "Clear All"
        }
    }

    fun clearallordelete(){
        if(isUpdateOrDelete){
            delete(subscriberToUpdateOrDelete)
        }else{
            clearAll()
        }
    }

    fun initupdateanddelete(subscriber: Subscriber){
        inputname.value = subscriber.name
        inputemail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveorupdatebutton.value = "Update"
        clearallordeletebutton.value = "Delete"
    }

    fun insert(subscriber: Subscriber) = viewModelScope.launch(IO) {
        repository.insertSubscriber(subscriber)
    }

    fun update(subscriber: Subscriber) = viewModelScope.launch(IO) {
        repository.updateSubscriber(subscriber)
        withContext(Dispatchers.Main){
            inputname.value = ""
            inputemail.value = ""
            isUpdateOrDelete = false
            saveorupdatebutton.value = "Save"
            clearallordeletebutton.value = "ClearAll"
        }
    }

    fun delete(subscriber: Subscriber) = viewModelScope.launch(IO) {
        repository.deleteSubscriber(subscriber)

        withContext(Dispatchers.Main){
            inputname.value = ""
            inputemail.value = ""
            isUpdateOrDelete = false
            saveorupdatebutton.value = "Save"
            clearallordeletebutton.value = "ClearAll"
        }
    }

    fun clearAll() = viewModelScope.launch(IO) {
        repository.deleteAll()
    }
}