package com.example.rendy.todolist.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.rendy.todolist.Activity.EditTodoActivity
import com.example.rendy.todolist.Activity.OnDeleteListener
import com.example.rendy.todolist.Model.Todo
import com.example.rendy.todolist.R
import io.realm.Realm
import io.realm.RealmModel
import kotlinx.android.synthetic.main.todo_list_item.*
import org.w3c.dom.Text

/**
 * Created by rendy on 22/06/17.
 */
class TodoAdapter(todos: ArrayList<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    lateinit var items: ArrayList<Todo>

    init {
        this.items = todos
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TodoViewHolder {
        val layoutInflater = LayoutInflater.from(parent!!.context)
        return TodoViewHolder(layoutInflater.inflate(R.layout.todo_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder?, position: Int) {
        holder?.bind(this.items[position], position)
    }

    class TodoViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val titleTv: TextView = itemView!!.findViewById<TextView>(R.id.todoTitle)
        val descriptionTv: TextView = itemView!!.findViewById<TextView>(R.id.todoDescription)
        val editButton: TextView = itemView!!.findViewById<TextView>(R.id.editButton)
        val deleteButton: TextView = itemView!!.findViewById<TextView>(R.id.deleteButton)
        val context: Context = itemView!!.context

        public fun bind (model: Todo, position: Int) {

            val parentWidth: Int = itemView.width
            val maxWidth: Int = parentWidth - 2 * deleteButton.width

            this.titleTv.maxWidth = maxWidth
            this.descriptionTv.maxWidth = maxWidth

            var titleText: String= model.title.toString()
            var descriptionText: String = model.description.toString()

            println(titleText.length)
            println(descriptionText.length)

            if (model.title!!.length > 30) {
                titleText = model.title!!.toString().substring(1, 30) + "..."
            } else if (model.description!!.length > 30){
               descriptionText = model.description!!.substring(1, 30) + "..."
            }

            this.titleTv.text = titleText
            this.descriptionTv.text = descriptionText

            this.editButton.setOnClickListener {
                val intent: Intent = Intent(context, EditTodoActivity::class.java)
                intent.putExtra("id", model.id)
                this.context.startActivity(intent)
            }

            this.deleteButton.setOnClickListener {
                val realm = Realm.getDefaultInstance()

                AlertDialog.Builder(this.context)
                        .setMessage("Yakin telah menyelesaikan todo ?")
                        .setPositiveButton("Ya", DialogInterface.OnClickListener { dialogInterface, i ->
                            realm.executeTransaction {
                                realm.where(Todo::class.java)
                                        .equalTo("id", model.id)
                                        .findFirst()
                                        .deleteFromRealm()

                                if (this.context is OnDeleteListener) {
                                    this.context.setOnDeleteListener()
                                }
                            }
                        }).setNegativeButton("Tidak", null)
                        .create()
                        .show()
            }
        }
    }
}