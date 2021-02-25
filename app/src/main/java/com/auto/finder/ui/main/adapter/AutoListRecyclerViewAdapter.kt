package com.auto.finder.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.auto.finder.databinding.AutoListItemEvenBinding
import com.auto.finder.databinding.AutoListItemOddBinding
import com.auto.finder.ui.BaseViewHolder
import com.auto.finder.ui.OnRecyclerItemClickListener

/**
 * [RecyclerView.Adapter] that displays data.
 */
class AutoListRecyclerViewAdapter: RecyclerView.Adapter<BaseViewHolder>() {

    private var autoList = ArrayList<String>()
    private var mOnItemClickListener: OnRecyclerItemClickListener? = null

    // construction method for initiating variables
    companion object {
        const val ODD = 1
        const val EVEN = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        // on the basis of variable used, we can change odd or even
        return when(viewType) {
            ODD -> AutoOddListViewHolder.from(parent)
            else -> AutoEvenViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val auto = autoList[position]
        holder.onBind(auto)
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(it, auto, position)
        }
    }

    override fun getItemCount(): Int = autoList.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int {
        return if (position%2 == 1) // odd even layout for adapter
            ODD
        else
            EVEN
    }

    /**
     * adding the data list received from to adapter
     * @param: [list] - used to populate the adapter items
     */
    fun updateAutoList(list: ArrayList<String>) {
        val size = autoList.size
        autoList.addAll(list)
        notifyItemRangeChanged(size, autoList.size)
    }

    /**
     * initiating the listener
     *
     * @param:[listener] - use this to assign the class level listener
     */
    fun setOnItemClickListener(listener: OnRecyclerItemClickListener) {
        mOnItemClickListener = listener
    }

    /**
     * View holder class for showing list
     * @param - [AutoListItemOddBinding] - variable use for binding the UI with adapter
     */
    class AutoOddListViewHolder(private val binding: AutoListItemOddBinding) : BaseViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): AutoOddListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AutoListItemOddBinding.inflate(layoutInflater, parent, false)
                return AutoOddListViewHolder(binding)
            }
        }

        override fun onBind(auto: String) {
            binding.auto = auto
            binding.executePendingBindings()
        }
    }

    /**
     * View holder class for showing even type view
     * @param - [AutoListItemEvenBinding] - variable use for binding the UI with adapter
     */
    class AutoEvenViewHolder(private val binding: AutoListItemEvenBinding) : BaseViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): AutoEvenViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AutoListItemEvenBinding.inflate(layoutInflater, parent, false)
                return AutoEvenViewHolder(binding)
            }
        }

        override fun onBind(auto: String) {
            binding.auto = auto
            binding.executePendingBindings()
        }
    }
}