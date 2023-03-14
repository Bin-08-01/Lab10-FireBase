package com.example.firebase.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.EmployeeModel
import com.example.firebase.R
import com.example.firebase.databinding.EmpListItemsBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class EmpAdapter(private val ds:ArrayList<EmployeeModel>): RecyclerView.Adapter<EmpAdapter.ViewHolder>() {
    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemCLick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener{
                clickListener.onItemCLick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.emp_list_items, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.apply {
            val txtView = findViewById<TextView>(R.id.tvEmpName)
            txtView.text = ds[position].empName
        }
    }

    override fun getItemCount(): Int {
        return ds.size
    }
}