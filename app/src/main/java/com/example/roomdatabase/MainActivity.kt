package com.example.roomdatabase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.databinding.ActivityMainBinding
import com.example.roomdatabase.db.Subscriber
import com.example.roomdatabase.db.SubscriberDatabase
import com.example.roomdatabase.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberviewModel: SubscriberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = SubscriberDatabase.instance(application).subscriberDao
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberviewModel = ViewModelProvider(this,factory)[SubscriberViewModel::class.java]
        binding.myViewModel = subscriberviewModel
        binding.lifecycleOwner = this

        initRecyclerView()
    }

    fun initRecyclerView(){
        binding.rvSubscriberlist.layoutManager = LinearLayoutManager(this)
        displaySubscribersList()
    }

    private fun subscriberitemclicked(selecteditem: Subscriber) {
        subscriberviewModel.initupdateanddelete(selecteditem)
    }

    fun displaySubscribersList() {
        subscriberviewModel.subscribers.observe(this, Observer {
            binding.rvSubscriberlist.adapter = MainActivityAdapter(it,{selecteditem:Subscriber -> subscriberitemclicked(selecteditem)})
        })
    }
}
