package com.app.fitness

import com.app.fitness.common.extention.timerFormat
import com.google.common.truth.Truth
import org.junit.Test

class TimerFormatTest {

    @Test
    fun `test timer convert format time for one second`(){
        val time = 1000L
        val format=  time.timerFormat()

        Truth.assertThat(format).isEqualTo("00:00:01")
    }
    @Test
    fun `test timer convert format time for one minutes`(){
        val time = 1000L * 60
        val format=  time.timerFormat()
        Truth.assertThat(format).isEqualTo("00:01:00")
    }

    @Test
    fun `test timer convert format time for second and minutes `(){
        val time = 1000L * 150
        val format=  time.timerFormat()
        Truth.assertThat(format).isEqualTo("00:02:30")
    }
    @Test
    fun `test timer convert format time for hours `(){
        val time = 1000L * 60 * 60
        val format=  time.timerFormat()
        Truth.assertThat(format).isEqualTo("01:00:00")
    }

    @Test
    fun `test timer convert format time for second and minutes and hours `(){
        val time = 1000L * 7506
        val format=  time.timerFormat()
        Truth.assertThat(format).isEqualTo("02:05:06")
    }


}