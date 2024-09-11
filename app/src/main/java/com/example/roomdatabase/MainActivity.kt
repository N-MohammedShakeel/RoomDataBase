package com.example.roomdatabase

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
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
    private lateinit var subscriberadapter: MainActivityAdapter

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

        subscriberviewModel.message.observe(this,Observer{
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this,it,Toast.LENGTH_LONG).show()
            }
        })
    }

    fun initRecyclerView(){
        binding.rvSubscriberlist.layoutManager = LinearLayoutManager(this)
        subscriberadapter = MainActivityAdapter({selectedItem:Subscriber->subscriberitemclicked(selectedItem)})
        binding.rvSubscriberlist.adapter = subscriberadapter
        displaySubscribersList()
    }

    private fun subscriberitemclicked(selecteditem: Subscriber) {
        subscriberviewModel.initupdateanddelete(selecteditem)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun displaySubscribersList() {
        //for Flow
//        subscriberviewModel.subscribers().observe(this, Observer {
//            subscriberadapter.setList(it)
//            subscriberadapter.notifyDataSetChanged()
//        })

        subscriberviewModel.subscribers.observe(this, Observer {
            subscriberadapter.setList(it)
            subscriberadapter.notifyDataSetChanged()
        })
    }
}
