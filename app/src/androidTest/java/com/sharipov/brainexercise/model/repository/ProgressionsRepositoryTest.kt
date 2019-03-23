package com.sharipov.brainexercise.model.repository

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

class ProgressionsRepositoryTest {

    @Test
    fun getProgressions() {
        val expected = 5
        val existed = ProgressionsRepository.getProgressions()[0].elements.size
        Assert.assertEquals(expected, existed)
    }
}