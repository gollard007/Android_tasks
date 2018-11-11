package com.gollard.task_0.activity

import android.app.Activity
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.gollard.task_0.R
import com.gollard.task_0.db.DbHelper
import com.gollard.task_0.db.Person
import com.gollard.task_0.db.PersonHelper

class PersonActivity : AppCompatActivity() {

    lateinit var name: EditText
    lateinit var surname: EditText
    lateinit var age: EditText
    lateinit var salary: EditText
    lateinit var add: Button

    lateinit var dbHelper: DbHelper
    lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person)
        dbHelper = DbHelper(this)
        db = dbHelper.writableDatabase
        initViews()
    }

    fun initViews() {
        name = findViewById(R.id.et_name)
        surname = findViewById(R.id.et_surname)
        age = findViewById(R.id.et_age)
        salary = findViewById(R.id.et_salary)
        add = findViewById(R.id.btn_add)
        add.setOnClickListener {
            if (name.text.isEmpty() || surname.text.isEmpty() || age.text.isEmpty()
                    || salary.text.isEmpty()) {
                Toast.makeText(this, "You have empty fields!", Toast.LENGTH_LONG).show()
            } else {
                val person = Person(name.text.toString(), surname.text.toString(),
                        Integer.valueOf(age.text.toString()), Integer.valueOf(salary.text.toString()))
                PersonHelper.addPerson(db, person)
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}
