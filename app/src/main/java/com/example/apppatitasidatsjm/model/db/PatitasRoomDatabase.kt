package com.example.apppatitasidatsjm.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.apppatitasidatsjm.model.db.dao.PersonaDao
import com.example.apppatitasidatsjm.model.db.entity.PersonaEntity

@Database(entities = [PersonaEntity::class], version = 1)
public abstract class PatitasRoomDatabase: RoomDatabase() {
    abstract fun personaDao(): PersonaDao

    companion object {
        @Volatile
        private var Instancia: PatitasRoomDatabase? = null
        fun getDatabase(context: Context): PatitasRoomDatabase {
            val tempInstancia = Instancia
            if(tempInstancia != null) {
                return tempInstancia
            }
            synchronized(this) {
                val instancia = Room.databaseBuilder(context.applicationContext,
                    PatitasRoomDatabase::class.java, "patitasdb").build()
                Instancia = instancia
                return instancia
            }
        }
    }
}