package com.app.fitness.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.app.fitness.data.model.Session
import com.app.fitness.data.model.Status
import com.app.fitness.data.model.Tracking
import com.google.common.truth.Truth
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class SessionDaoTest : TestCase() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var database: AppDatabase
    private lateinit var sessionDao: SessionDao


    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        sessionDao = database.fitnessDea()
    }


    @Test
    fun saveSession() = runBlocking {
        val session = Session(status = Status.START)
        sessionDao.saveSession(session)

        val result = sessionDao.getLastSession()
        Truth.assertThat(result).isNotEqualTo(null)
    }


    @Test
    fun updateSession() = runBlocking {
        saveSession()
        val result = sessionDao.getLastSession()
        Truth.assertThat(result).isNotEqualTo(null)

        result?.apply {
           this. status= Status.START
            steps=100
            duration=1222L
        }

        result?.let { sessionDao.updateSession(it) }

        val confirmUpdate= sessionDao.getLastSession()
        Truth.assertThat(confirmUpdate?.steps).isEqualTo(100)


    }

    @Test
    fun saveTrackingForSession()= runBlocking {
        saveSession()

        val result = sessionDao.getLastSession()

        sessionDao.saveLocation(
            Tracking(
                latitude = 100.0,
                longitude = 100.0,
                tripId = result?.id!!
            )
        )

        val sessionDetails = sessionDao.getSessionDetails(result.id!!)

        Truth.assertThat(sessionDetails.session).isNotNull()
        Truth.assertThat(sessionDetails.locations).isNotNull()
        Truth.assertThat(sessionDetails.locations).isNotEmpty()

    }



    @After
    fun closeDatabase() {
        database.close()
    }
}