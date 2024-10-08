package com.example.roomdatabase.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Subscriber::class], version = 1, exportSchema = false)
abstract class SubscriberDatabase: RoomDatabase() {
    abstract val subscriberDao : SubscriberDao

    companion object{
        @Volatile
        private var INSTANCE : SubscriberDatabase? = null
        fun instance(context: Context):SubscriberDatabase{
           synchronized(this){
               var instance = INSTANCE
               if (instance == null){
                   instance = Room.databaseBuilder(
                       context.applicationContext,
                       SubscriberDatabase::class.java,
                       "subscriber_data_database"
                   ).build()
                   INSTANCE = instance
               }
               return instance
           }
        }
    }
}