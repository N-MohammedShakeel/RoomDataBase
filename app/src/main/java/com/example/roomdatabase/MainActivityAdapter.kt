package com.example.roomdatabase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.databinding.SubscriberItemBinding
import com.example.roomdatabase.db.Subscriber

class MainActivityAdapter(private val subscriberstList: List<Subscriber>):RecyclerView.Adapter<MainActivityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : SubscriberItemBinding = DataBindingUtil.inflate(layoutInflater,R.layout.subscriber_item,parent,false)
        return MainActivityViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return subscriberstList.size
    }

    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
        holder.bind(subscriberstList[position])
    }

}

class MainActivityViewHolder(val binding: SubscriberItemBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(subscriber: Subscriber){
        binding.tvname.text = subscriber.name
        binding.tvemail.text = subscriber.email
    }

}