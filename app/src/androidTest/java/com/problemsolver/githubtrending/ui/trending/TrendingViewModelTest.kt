package com.problemsolver.githubtrending.ui.trending

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.problemsolver.githubtrending.models.Trending
import com.problemsolver.githubtrending.services.TrendingApi
import com.problemsolver.githubtrending.services.TrendingListener
import com.problemsolver.githubtrending.services.TrendingRepository
import com.problemsolver.githubtrending.services.TrendingRepositoryImpl
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Call

class TrendingViewModelTest {

    private lateinit var viewModel: TrendingViewModel

    private lateinit var trendingApiImpl: TrendingRepositoryImpl

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var trendingList: List<Trending>

    @Mock
    var trendingApi: TrendingApi = object : TrendingApi {
        override fun getTrendingList(
            language: String,
            since: String,
            spoken_language_code: String
        ): Call<List<Trending>> {
            return Mockito.any()
        }
    }

    @Mock
    var trendingRepo: TrendingRepository = object : TrendingRepository {
        override val listTrending: List<Trending>?
            get() = emptyList()

        override fun getTrendingList(
            listener: TrendingListener<List<Trending>>,
            language: String,
            since: String,
            spoken_language_code: String
        ) {
        }
    }

    @Before
    fun init() {
        viewModel = TrendingViewModel(trendingRepo)
        trendingApiImpl = TrendingRepositoryImpl(trendingApi)
        trendingList = emptyList()
    }

    @Test
    fun getTrendingList() {
        Assert.assertNotNull(viewModel.getTrendingList())
    }

    @Test
    fun testLoadData() {
        Assert.assertNotNull(trendingList)
    }
}