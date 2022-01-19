package com.minhee.mvvm_crud_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.minhee.mvvm_crud_app.databinding.ListItemBinding
import com.minhee.mvvm_crud_app.db.Subscriber

class MyRecyclerViewAdapter(private val clickListener: (Subscriber) -> Unit) :
    RecyclerView.Adapter<MyViewHolder>(){
        private val subscribersList = ArrayList<Subscriber>()

    //뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    //뷰가 묶였을 때 데이터를 뷰홀더에 넘겨준다.
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribersList[position], clickListener)
    }

    //보여줄 목록의 개수
    override fun getItemCount(): Int {
        return subscribersList.size
    }

    fun setList(subscribers: List<Subscriber>) {
        subscribersList.clear()
        subscribersList.addAll(subscribers)

    }
}

class MyViewHolder(val binding : ListItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit) {
        binding.nameTextView.text = subscriber.name
        binding.emailTextView.text = subscriber.email
        binding.listItemLayout.setOnClickListener {
            clickListener(subscriber)
        }
    }
}