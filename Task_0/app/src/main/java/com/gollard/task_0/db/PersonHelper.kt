package com.gollard.task_0.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class PersonHelper {

    object PersonEntry : BaseColumns {
        const val TABLE_NAME = "person"
        const val NAME_TITLE = "name"
        const val SURNAME_TITLE = "surname"
        const val AGE_TITLE = "age"
        const val SALARY_TITLE = "salary"
    }

    companion object {
        const val SQL_CREATE_SCRIPT =
                "CREATE TABLE ${PersonEntry.TABLE_NAME} (" +
                        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                        "${PersonEntry.NAME_TITLE} TEXT," +
                        "${PersonEntry.SURNAME_TITLE} TEXT," +
                        "${PersonEntry.AGE_TITLE} INTEGER," +
                        "${PersonEntry.SALARY_TITLE} INTEGER)"

        const val SQL_DELETE_SCRIPT = "DROP TABLE IF EXIST ${PersonEntry.TABLE_NAME}"

        fun addPerson(db: SQLiteDatabase, person: Person) {
            val values = ContentValues().apply {
                put(PersonEntry.NAME_TITLE, person.name)
                put(PersonEntry.SURNAME_TITLE, person.surname)
                put(PersonEntry.AGE_TITLE, person.age)
                put(PersonEntry.SALARY_TITLE, person.salary)
            }
            db.insert(PersonEntry.TABLE_NAME, null, values)
        }

        fun deletePerson(db: SQLiteDatabase, person: Person) {
            val selection = "${PersonEntry.NAME_TITLE} LIKE ? AND " +
                    "${PersonEntry.SURNAME_TITLE} LIKE ? AND " +
                    "${PersonEntry.AGE_TITLE} LIKE ? AND " +
                    "${PersonEntry.SALARY_TITLE} LIKE ? "

            val selectionArgs = arrayOf(person.name, person.surname, person.age.toString(),
                    person.salary.toString())
            db.delete(PersonEntry.TABLE_NAME, selection, selectionArgs)
        }

        fun getPersons(db: SQLiteDatabase): ArrayList<Person> {
            val persons = ArrayList<Person>()

            val projection = arrayOf(PersonEntry.NAME_TITLE, PersonEntry.SURNAME_TITLE,
                    PersonEntry.AGE_TITLE, PersonEntry.SALARY_TITLE)

            val cursor = db.query(
                    PersonEntry.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            )

            with(cursor) {
                while (moveToNext()) {
                    val name = getString(getColumnIndexOrThrow(PersonEntry.NAME_TITLE))
                    val surname = getString(getColumnIndexOrThrow(PersonEntry.SURNAME_TITLE))
                    val age = getInt(getColumnIndexOrThrow(PersonEntry.AGE_TITLE))
                    val salary = getInt(getColumnIndexOrThrow(PersonEntry.SALARY_TITLE))
                    persons.add(Person(name, surname, age, salary))
                }
            }

            return persons
        }
    }

}