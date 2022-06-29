package com.example.matchball.mymatches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.matchball.databinding.MyMatchItemsBinding
import com.example.matchball.model.MatchRequest
import com.example.matchball.yourmatchrequest.list.YourRequestAdapter

class MyMatchListAdapter(private var myMatchList : ArrayList<MatchRequest>)
    : RecyclerView.Adapter<MyMatchListAdapter.MyViewHolder>(){

    private lateinit var listerner: OnItemClickListerner

    interface OnItemClickListerner {
        fun onItemClick(data: MatchRequest)
    }

    fun setOnItemClickListerner(listerner: OnItemClickListerner) {
        this.listerner = listerner
    }

    fun addNewData(list: ArrayList<MatchRequest>) {
        myMatchList = list
        notifyDataSetChanged()
    }

    fun addMoreData(list: ArrayList<MatchRequest>) {
        myMatchList.addAll(list)
        notifyDataSetChanged()
    }

    fun remove(matchRequest: MatchRequest) {
    }

    class MyViewHolder(
        private val myMatchItemsBinding : MyMatchItemsBinding,
        private val listerner: OnItemClickListerner
    ) : RecyclerView.ViewHolder(myMatchItemsBinding.root) {
        fun bind(data : MatchRequest) {
            with(myMatchItemsBinding) {
                tvTeamName.text = data.teamName
                tvPitch.text = data.pitch
                tvTime.text = data.time
                tvAmount.text = data.people
                tvPhone.text = data.phone

                myMatchItemsBinding.items.setOnClickListener{
                    listerner.onItemClick(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val myMatchItemsBinding = MyMatchItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(myMatchItemsBinding, listerner)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(myMatchList[position])
    }

    override fun getItemCount(): Int {
        return myMatchList.size
    }
}