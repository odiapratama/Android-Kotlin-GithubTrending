package com.problemsolver.githubtrending.ui.trending

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import com.problemsolver.githubtrending.R
import com.problemsolver.githubtrending.databinding.ActivityTrendingBinding
import com.problemsolver.githubtrending.services.TrendingRepository
import com.problemsolver.githubtrending.utils.DateUtils.Companion.DATE_TIME_PATTERN
import com.problemsolver.githubtrending.utils.DateUtils.Companion.LOCALE_ENGLISH
import com.problemsolver.githubtrending.utils.isInternetAvailable
import com.problemsolver.githubtrending.utils.stringToDate
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import com.problemsolver.githubtrending.base.DataBindingActivity
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class TrendingActivity : DataBindingActivity() {

    private val trendingViewModel by viewModel<TrendingViewModel>()
    private val binding: ActivityTrendingBinding by binding(R.layout.activity_trending)
    private val trendingAdapter: TrendingAdapter by lazy { TrendingAdapter(emptyList(), this) }
    private val trendingRepo: TrendingRepository by inject()
    private var validUntil = 0L
    private var now: Long = Date().time
    private lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            ProviderInstaller.installIfNeeded(this)
        } catch (e: GooglePlayServicesRepairableException) {
            GoogleApiAvailability.getInstance()
                .showErrorNotification(this, e.connectionStatusCode)
        } catch (e: GooglePlayServicesNotAvailableException) {
        }
        initData()
        initView()
        initListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.nav_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sortByStars -> {
                trendingAdapter.updateData(
                    trendingRepo.getCacheTrendingList()?.sortedByDescending { it.stars })
            }
            R.id.sortByName -> {
                trendingAdapter.updateData(
                    trendingRepo.getCacheTrendingList()?.sortedBy { it.name })
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        validUntil = stringToDate(
            trendingRepo.getCacheFetchTime() ?: "",
            DATE_TIME_PATTERN,
            LOCALE_ENGLISH
        ).time
        runTimer()
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }

    private fun initView() {
        with(binding) {
            lifecycleOwner = this@TrendingActivity
            vm = trendingViewModel
            setSupportActionBar(this.toolbar)
            rvTrending.adapter = trendingAdapter
        }
    }

    private fun initData() {
        validUntil = stringToDate(
            trendingRepo.getCacheFetchTime() ?: "",
            DATE_TIME_PATTERN,
            LOCALE_ENGLISH
        ).time
        loadData()
        trendingViewModel.trendingList.observe(this, Observer {
            trendingAdapter.updateData(it)
        })
        trendingViewModel.isResponse.observe(this, Observer {
            showLoading(false)
            if (it) {
                showNoConnection(false)
                onResume()
            } else {
                showNoConnection(true)
            }
        })
    }

    private fun initListener() {
        binding.srlTrending.setOnRefreshListener {
            showNoConnection(false)
            when {
                isInternetAvailable(this) -> {
                    showLoading(true)
                    trendingViewModel.getTrendingList()
                }
                trendingRepo.getCacheFetchTime() != null -> {
                    trendingAdapter.updateData(trendingRepo.getCacheTrendingList())
                }
                else -> {
                    showNoConnection(true)
                }
            }
            binding.srlTrending.isRefreshing = false
        }
        binding.tvRetry.setOnClickListener {
            if (isInternetAvailable(this)) {
                loadData()
            }
        }
    }

    private fun loadData() {
        showNoConnection(false)
        if (isInternetAvailable(this)) {
            if (now <= validUntil && trendingRepo.getCacheTrendingList() != null) {
                trendingAdapter.updateData(trendingRepo.getCacheTrendingList())
            } else {
                showLoading(true)
                trendingViewModel.getTrendingList()
            }
        } else {
            if (now <= validUntil && trendingRepo.getCacheTrendingList() != null) {
                trendingAdapter.updateData(trendingRepo.getCacheTrendingList())
            } else {
                showNoConnection(true)
            }
        }
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            with(binding) {
                rvTrending.visibility = View.GONE
                shimmerTrending.visibility = View.VISIBLE
                shimmerTrending.startShimmer()
            }
        } else {
            with(binding) {
                shimmerTrending.startShimmer()
                shimmerTrending.visibility = View.GONE
                rvTrending.visibility = View.VISIBLE
            }
        }
    }

    private fun showNoConnection(show: Boolean) {
        if (show) {
            binding.clNoConnection.visibility = View.VISIBLE
            binding.rvTrending.visibility = View.GONE
        } else {
            binding.clNoConnection.visibility = View.GONE
            binding.rvTrending.visibility = View.VISIBLE
        }
    }

    private fun runTimer() {
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    now = Date().time
                    if (now > validUntil) {
                        timer.cancel()
                        loadData()
                    }
                }
            }
        }, 0, 1000)
    }
}
