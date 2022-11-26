package com.app.fitness

import com.app.fitness.common.extention.getDistanceCovered
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class CalculateDistanceTest {


    @Test
    fun `convert steps to distance with 0 value`(){

        val steps= 0
        val distance = steps.getDistanceCovered().toDoubleOrNull()

        assertThat(distance).isEqualTo(0.0)
    }

    @Test
    fun `convert steps to distance with value bigger than 0`(){

        val steps= 100
        val distance = steps.getDistanceCovered().toDoubleOrNull()
        assertThat(distance).isGreaterThan(0.0)
    }


    @Test
    fun `check steps distance accuracy`(){

        val steps= 100
        val distance = steps.getDistanceCovered().toDoubleOrNull()

        assertThat(distance).isGreaterThan(100*.5)

        assertThat(distance).isLessThan(100*.8)


    }




}