package com.example.matchball.mymatches.myrequest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.matchball.databinding.YourRequestItemsBinding
import com.example.matchball.model.MatchRequest

class MyRequestAdapter(private var myRequestList : ArrayList<MatchRequest>)
    : RecyclerView.Adapter<MyRequestAdapter.MyViewHolder>() {

    private lateinit var listerner: OnItemClickListerner

    interface OnItemClickListerner {
        fun onItemClick(data: MatchRequest)
    }

    fun addNewData(list: ArrayList<MatchRequest>) {
        myRequestList = list
        notifyDataSetChanged()
    }

    fun addMoreData(list: ArrayList<MatchRequest>) {
        myRequestList.addAll(list)
        notifyDataSetChanged()
    }

    fun remove(matchRequest: MatchRequest) {
    }

    fun setOnItemClickListerner(listerner: OnItemClickListerner) {
        this.listerner = listerner
    }

    class MyViewHolder(
        private val yourRequestItemsBinding: YourRequestItemsBinding,
        private val listerner: OnItemClickListerner
        ) : RecyclerView.ViewHolder(yourRequestItemsBinding.root) {
        fun bind(data : MatchRequest) {
            with(yourRequestItemsBinding) {
                tvTime.text = data.time
                tvAmount.text = data.people
                tvPitch.text = data.pitch

                yourRequestItemsBinding.items.setOnClickListener {
                    listerner.onItemClick(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val yourRequestItems =
            YourRequestItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(yourRequestItems, listerner)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(myRequestList[position])
    }

    override fun getItemCount(): Int {
        return myRequestList.size
    }
}