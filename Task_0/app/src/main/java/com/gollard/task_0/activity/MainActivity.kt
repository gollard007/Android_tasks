package com.gollard.task_0.activity

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.gollard.task_0.R
import com.gollard.task_0.db.DbHelper
import com.gollard.task_0.db.Person
import com.gollard.task_0.db.PersonHelper

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE: Int = 100
    }

    lateinit var dbHelper: DbHelper
    lateinit var db: SQLiteDatabase

    lateinit var button: Button
    lateinit var recyclerView: RecyclerView
    lateinit var items: ArrayList<Person>
    lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = DbHelper(this)
        db = dbHelper.writableDatabase
        button = findViewById(R.id.btn_add)
        button.setOnClickListener {
            startActivityForResult(Intent(this, PersonActivity::class.java), REQUEST_CODE)
        }
        recyclerView = findViewById(R.id.rv_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(db)
        recyclerView.adapter = adapter
        updateItems()
    }

    fun updateItems() {
        items = PersonHelper.getPersons(db)
        adapter.setItems(items)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            updateItems()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}
