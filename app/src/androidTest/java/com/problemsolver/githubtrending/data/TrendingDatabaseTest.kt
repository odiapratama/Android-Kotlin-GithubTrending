package com.problemsolver.githubtrending.data

import androidx.test.platform.app.InstrumentationRegistry
import com.problemsolver.githubtrending.models.Trending
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class TrendingDatabaseTest {

    private lateinit var trendingDatabase: TrendingDatabase

    private lateinit var trendingList: List<Trending>

    private val fakeTime = Mockito.anyString()

    @Before
    fun setUp() {
        trendingDatabase = TrendingDatabase(InstrumentationRegistry.getInstrumentation().context)
        trendingList = Mockito.anyList()
    }

    @Test
    fun saveTrendingList() {
        // set data
        assertNotNull(trendingList)
        trendingDatabase.setTrendingList(trendingList)
        assertNotNull(trendingDatabase.setTrendingList(trendingList))

        // get data
        assertNotNull(trendingDatabase.getTrendingList())
    }

    @Test
    fun saveFetchTime() {
        // set data
        assertNotNull(trendingList)
        trendingDatabase.setFetchTime(fakeTime)
        assertNotNull(trendingDatabase.setFetchTime(fakeTime))

        // get data
        assertNotNull(trendingDatabase.getFetchTime())
    }
}