package com.problemsolver.githubtrending.ui.trending

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.problemsolver.githubtrending.R
import com.problemsolver.githubtrending.databinding.ListItemTrendingBinding
import com.problemsolver.githubtrending.models.Trending
import com.problemsolver.githubtrending.utils.slideAnimation

class TrendingAdapter(private var trendingList: List<Trending>, private val context: Context) :
    RecyclerView.Adapter<TrendingAdapter.BindingHolder>() {

    private var selectedItem: Int? = null

    class BindingHolder(var binding: ListItemTrendingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemTrendingBinding.inflate(layoutInflater, parent, false)
        return BindingHolder(binding)
    }

    override fun getItemCount(): Int {
        return trendingList.size
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val data = trendingList[position]
        holder.binding.item = data
        holder.binding.clInformation.visibility = View.GONE
        holder.binding.ivLanguage.background = createCircleDrawable(data.languageColor)
        holder.binding.tvDescription.typeface = Typeface.createFromAsset(
            context.assets, context.getString(
                R.string.pingfang_font_path
            )
        )
        holder.binding.cvTrending.setOnClickListener {
            if (selectedItem != position) {
                slideAnimation(holder.binding.clInformation, true)
                selectedItem?.let {
                    notifyItemChanged(it)
                }
                selectedItem = position
            } else {
                selectedItem = null
                slideAnimation(holder.binding.clInformation, false)
            }
        }
    }

    fun updateData(newList: List<Trending>?) {
        newList?.let {
            trendingList = it
            notifyDataSetChanged()
        }
    }

    private fun createCircleDrawable(color: String?): ShapeDrawable {
        return ShapeDrawable(OvalShape()).apply {
            intrinsicHeight = 10
            intrinsicWidth = 10
            bounds = Rect(30, 30, 30, 30)
            color?.let {
                paint.color = Color.parseColor(it)
            }
        }
    }
}