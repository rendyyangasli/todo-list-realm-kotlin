package com.example.rendy.todolist.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.Toast
import com.example.rendy.todolist.Model.Todo
import com.example.rendy.todolist.R
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add_todo.*
import java.util.*

class AddTodoActivity : AppCompatActivity() {
    val realm by lazy { Realm.getDefaultInstance() }
    val todo: Todo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_add_todo)

        if (supportActionBar != null) {
            supportActionBar!!.title = getString(R.string.add_todo_title)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        submitButton.setOnClickListener {
            if (this.formValidated()) {
                this.addTodo()
                Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, "Cek Input yang anda masukkan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun addTodo () {
        this.realm.executeTransaction {
            val todo = this.realm.createObject(Todo::class.java, UUID.randomUUID().toString())
            todo.title = titleInput.text.toString()
            todo.description = descriptionInput.text.toString()
        }
    }

    fun formValidated (): Boolean {
        if (titleInput.text.toString() == "") {
            return false
        }
        if (descriptionInput.text.toString() == "") {
            return false
        }
        return true
    }
}
