package com.tecsup.edu.healthylife.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tecsup.edu.healthylife.authentication.AuthDAO
import com.tecsup.edu.healthylife.authentication.AuthEntity

// Clase de la base de datos
@Database(entities = [AuthEntity::class], version = 1)
abstract class HealthyLifeDataBase : RoomDatabase() {
    abstract fun authDao() : AuthDAO

    companion object {

        private const val DATABASE_NAME = "tecsup_database"

        @Volatile
        private var INSTANCE: HealthyLifeDataBase? = null

        fun getInstance(context: Context): HealthyLifeDataBase? {
            INSTANCE
                ?: synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        HealthyLifeDataBase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            return INSTANCE
        }


    }
}