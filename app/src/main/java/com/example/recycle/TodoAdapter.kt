package com.example.recycle

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recycle.databinding.ListItemBinding

class TodoAdapter(private val viewModel: TodoViewModel) :
    ListAdapter<Todo, TodoAdapter.MyViewHolder>(TodoDiffCallback()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater)

        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: TodoAdapter.MyViewHolder, position: Int) {
        holder.todoText.text = getItem(holder.adapterPosition).task

        //menghapus
        holder.delBtn.setOnClickListener{
            viewModel.removeTodo(holder.adapterPosition)


        }

        //mengedit
        holder.ediBtn.setOnClickListener {
            val context = holder.itemView.context
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.edit_item, null)

            //mengambil data sebelumnya
            val prevText = getItem(holder.adapterPosition)
            val editText = view.findViewById<TextView>(R.id.editText)
            editText.text = prevText.task

            //dialog
            var alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("edit Item")
                .setView(view)
                .setPositiveButton("Update", DialogInterface.OnClickListener{
                    dialog, id ->

                    //edit
                    val editText = editText.text.toString()
                    viewModel.updateTodo(holder.adapterPosition, editText)
                    holder.todoText.text = editText

                })
                .setNegativeButton("cancel", DialogInterface.OnClickListener { dialog, id ->
                })
            alertDialog.create().show()
        }
    }

    //override fun getItemCount() = viewModel.todos.value!!.size

    class MyViewHolder (val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val todoText = binding.todoItem
        val delBtn = binding.btnDelete
        val ediBtn = binding.btnEdit
    }
}
class TodoDiffCallback : DiffUtil.ItemCallback<Todo>(){
    override fun areItemsTheSame(p0: Todo, p1: Todo): Boolean {
        return p0.id == p1.id
    }

    override fun areContentsTheSame(p0: Todo, p1: Todo): Boolean {
        return p0.equals(p1)
    }

}