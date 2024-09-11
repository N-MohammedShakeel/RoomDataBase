package com.example.roomdatabase

import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.db.Event
import com.example.roomdatabase.db.Subscriber
import com.example.roomdatabase.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.intellij.lang.annotations.Pattern

class SubscriberViewModel(private val repository: SubscriberRepository): ViewModel() {

    val subscribers = repository.subscribers
    //for flow
//    fun subscribers() = liveData {
//        repository.subscribers.collect {
//            emit(it)
//        }
//    }


    var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete : Subscriber

    private var statusMessage = MutableLiveData<Event<String>>()

    var message : LiveData<Event<String>>
        get() = statusMessage
        set(value) {}

     val inputname = MutableLiveData<String>()
     val inputemail = MutableLiveData<String>()

    val saveorupdatebutton = MutableLiveData<String>()
    val clearallordeletebutton = MutableLiveData<String>()

    init {
        saveorupdatebutton.value = "Save"
        clearallordeletebutton.value = "Clear All"
    }

    fun saveorupdate(){

        if (inputname.value == null) {
            statusMessage.value = Event("Please enter name")
        }else if (inputemail.value == null) {
            statusMessage.value = Event("Please enter email")
        }else if(!Patterns.EMAIL_ADDRESS.matcher(inputemail.value!!).matches()){
            statusMessage.value = Event("Please enter correct email")
        }else{
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
        val newRowId = repository.insertSubscriber(subscriber)
        withContext(Dispatchers.Main){
            statusMessage.value = Event("Subscriber Inserted Successfully $newRowId")
        }
    }

    fun update(subscriber: Subscriber) = viewModelScope.launch(IO) {
        repository.updateSubscriber(subscriber)
        withContext(Dispatchers.Main){
            inputname.value = ""
            inputemail.value = ""
            isUpdateOrDelete = false
            saveorupdatebutton.value = "Save"
            clearallordeletebutton.value = "ClearAll"
            statusMessage.value = Event("Subscriber Updated Successfully")

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
            statusMessage.value = Event("Subscriber Deleted Successfully")
        }
    }

    fun clearAll() = viewModelScope.launch(IO) {
        repository.deleteAll()
        withContext(Dispatchers.Main){
            statusMessage.value = Event("All Subscribers Deleted Successfully")
        }

    }
}