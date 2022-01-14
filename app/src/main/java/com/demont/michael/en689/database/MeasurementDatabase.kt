package com.demont.michael.en689.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demont.michael.en689.DateConverter

@Database (entities = [Measurement::class], version = 1,  exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class MeasurementDatabase : RoomDatabase() {

    abstract val measurementDatabaseDao : MeasurementDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: MeasurementDatabase? = null

        fun getInstance (context: Context): MeasurementDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MeasurementDatabase::class.java,
                        "metingen_verleden_database")

                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}