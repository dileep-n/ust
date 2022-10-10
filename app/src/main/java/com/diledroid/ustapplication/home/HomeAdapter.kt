package com.diledroid.ustapplication.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diledroid.ustapplication.data.model.DeviceModel
import com.diledroid.ustapplication.databinding.RecyclerviewItemBinding


/*
Adapter class responsible for the recycler view in home fragment
 */
class HomeAdapter(private val listener: HomeItemListener):
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private val items = ArrayList<DeviceModel>()

    // this interface is responsible for the click listener in recycler view
    interface HomeItemListener {
        fun onClickedImage(deviceItem: DeviceModel)
    }

    //based on each search the filtered list are passed to this method from home fragment
    fun setItems(items: ArrayList<DeviceModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class HomeViewHolder(private val itemBinding: RecyclerviewItemBinding,
    private val listener: HomeItemListener):
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener{
        private lateinit var deviceDetail: DeviceModel

        init {
            itemBinding.root.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            listener.onClickedImage(deviceDetail)
        }

        fun bind(item: DeviceModel) {
            this.deviceDetail = item
            itemBinding.nameText.text = item.deviceName
            itemBinding.ipText.text = item.deviceIP

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding: RecyclerviewItemBinding = RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return HomeViewHolder(binding,listener)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        return holder.bind(items[position])
    }

    override fun getItemCount(): Int {
       return items.size
    }

}