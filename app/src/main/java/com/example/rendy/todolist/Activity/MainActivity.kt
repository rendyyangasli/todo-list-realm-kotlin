package com.example.rendy.todolist.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.LayoutInflaterCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ItemDecoration
import android.view.Window
import com.example.rendy.todolist.Adapter.TodoAdapter
import com.example.rendy.todolist.Model.Todo
import com.example.rendy.todolist.R
import com.mikepenz.iconics.context.IconicsContextWrapper
import com.mikepenz.iconics.context.IconicsLayoutInflater
import com.mikepenz.iconics.context.IconicsLayoutInflater2
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnDeleteListener  {

    val realm by lazy { Realm.getDefaultInstance() }
    public lateinit var adapter: TodoAdapter

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(layoutInflater, IconicsLayoutInflater2(delegate))
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_ACTION_BAR)
        supportActionBar!!.hide()

        setContentView(R.layout.activity_main)
        addTodoFab.setOnClickListener { addTodo() }

        val todos: ArrayList<Todo> = this.getTodos()
        this.adapter = TodoAdapter(todos)

        todos_rv.layoutManager = LinearLayoutManager(this)
        todos_rv.adapter = this.adapter
    }

    private fun addTodo () {
        startActivity(Intent(this, AddTodoActivity::class.java))
    }

    private fun getTodos (): ArrayList<Todo>{
        return ArrayList(this.realm.where(Todo::class.java).findAll())
    }

    override fun setOnDeleteListener() {
        this.adapter.items = getTodos()
        this.adapter.notifyDataSetChanged()
    }
}
