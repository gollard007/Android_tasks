package com.gollard.task_0.activity

import android.database.sqlite.SQLiteDatabase
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.gollard.task_0.R
import com.gollard.task_0.db.Person
import com.gollard.task_0.db.PersonHelper

class Adapter(val db: SQLiteDatabase): RecyclerView.Adapter<Adapter.ViewHolder>() {

    val items: ArrayList<Person> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    fun setItems(items: ArrayList<Person>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(items[position].name + " " + items[position].surname)
        holder.age.setText("Age: " + items[position].age)
        holder.salary.setText("$ " + items[position].salary)
        holder.button.setOnClickListener {
            val person = items.removeAt(position)
            PersonHelper.deletePerson(db, person)
            notifyDataSetChanged()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tv_full_name) as TextView
        val age: TextView = view.findViewById(R.id.tv_age) as TextView
        val salary: TextView = view.findViewById(R.id.tv_salary) as TextView
        val button: Button = view.findViewById(R.id.btn_delete) as Button
    }
}