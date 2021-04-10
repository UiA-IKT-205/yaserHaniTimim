package com.example.prosjektoppgave1

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_addtodolist.*
import kotlinx.android.synthetic.main.activity_addtodolist.view.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_todo.*
import kotlinx.android.synthetic.main.item_todo.view.*
import kotlinx.android.synthetic.main.new_list.view.*


class TodoList_Adapter (

    private val todos: MutableList<Todo>,

) : RecyclerView.Adapter<TodoList_Adapter.TodoViewHolder>(){

        class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {

            return TodoViewHolder(

                LayoutInflater.from(parent.context).inflate(

                    R.layout.item_todo,
                    parent,
                    false
                )
            )
        }


        fun addTodo(todo: Todo){
            todos.add(todo)
            notifyItemInserted(todos.size-1)


        }


    fun update(ItemTodo:List<Todo>){
        for(l in ItemTodo){
            todos.add(l)
            notifyItemInserted(todos.size-1)
        }
    }





    fun deleteDoneTodo() {
            todos.removeAll { todo ->
                todo.isChecked
            }
            notifyDataSetChanged()

        }


        private fun toggleStrikThrough(tvTodoTitle: TextView, isChecked: Boolean) {
            if(isChecked){
                tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            }
        }




        override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
            val curentTodo= todos[position]

            holder.itemView.apply {
                tvitemTodo.text = curentTodo.item_title
                cbdone.isChecked = curentTodo.isChecked
                toggleStrikThrough(tvitemTodo,curentTodo.isChecked)
                cbdone.setOnCheckedChangeListener { _, isChecked ->
                    toggleStrikThrough(tvitemTodo,isChecked)
                    curentTodo.isChecked = !curentTodo.isChecked
                    val checked = curentTodo.item_title
                    val intent = Intent(context,AddTodoListActivity::class.java)
                    intent.putExtra("checked", checked)

                }
                delete_done_item.setOnClickListener {

                    deleteDoneTodo()

                }
                setOnClickListener {

                }

                cbdone.setOnClickListener(View.OnClickListener{

                })

            }
        }



        override fun getItemCount(): Int {

            return todos.size

        }
}