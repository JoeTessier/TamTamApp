package com.example.josephtessier.tamtamapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast


class ThemeAdapter (list: ArrayList<Theme>, private val context: Context): RecyclerView.Adapter<ThemeAdapter.ViewHolder>(){

    private var listAdapter = list

    override fun getItemCount(): Int {
        return listAdapter.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.checkbox.text = listAdapter[position].nomTheme
        holder.checkbox.isChecked = listAdapter[position].isSelected

        holder.checkbox.tag = position
        holder.checkbox.setOnClickListener(View.OnClickListener {
            val pos = holder.checkbox.tag as Int
            Toast.makeText(context, listAdapter[pos].nomTheme + " clicked", Toast.LENGTH_SHORT).show()

            listAdapter[pos].isSelected = !listAdapter[pos].isSelected
        })
    }




    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var checkbox: CheckBox = itemView.findViewById(R.id.checkboxTheme)
    }
}

