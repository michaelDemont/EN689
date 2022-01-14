package com.demont.michael.en689

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.demont.michael.en689.database.Measurement
import com.demont.michael.en689.database.MeasurementDatabase
import com.demont.michael.en689.database.MeasurementDatabaseDao

import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class MeasurementDatabaseTest {

    private lateinit var mesDao: MeasurementDatabaseDao
    private lateinit var db: MeasurementDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, MeasurementDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
       mesDao = db.measurementDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetMeasurement() {
        val mes = Measurement()
        mesDao.insert(mes)
        val mesNu = mesDao.getHuidigeMeting("", mes.date)
        assertEquals(mesNu?.conform, -1)
    }
}
