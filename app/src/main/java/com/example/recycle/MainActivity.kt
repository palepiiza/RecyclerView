package com.example.recycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recycle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: TodoAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var viewModel: TodoViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(TodoViewModel::class.java)



        viewManager = LinearLayoutManager(this)
        viewAdapter = TodoAdapter(viewModel)
        recyclerView = binding.myRecycleView

        recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
        binding.btnNew.setOnClickListener {
            viewModel.addTodo(binding.newText.text.toString())
            binding.newText.setText(null)
        }

        viewModel.todos.observe(this, Observer {
            list -> viewAdapter.submitList(list.toMutableList())
        })
    }
}


