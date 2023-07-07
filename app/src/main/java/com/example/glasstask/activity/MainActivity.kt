package com.example.glasstask.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.glasstask.adapter.TaskAdapter
import com.example.glasstask.database.TaskDataBase
import com.example.glasstask.databinding.ActivityMainBinding
import com.example.glasstask.model.TaskItem
import com.example.glasstask.repo.Repository
import com.example.glasstask.utility.TaskResponce
import com.example.glasstask.viewmodel.TaskViewModel
import com.example.glasstask.viewmodel.TaskViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var dataBinding : ActivityMainBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        initializeViewModel()

        val taskItem = TaskItem(0, "Homework", "Do the home work", false)

        taskViewModel.insertTask(taskItem)


        fetchTaskItemData()


    }

    private fun fetchTaskItemData() {
        taskViewModel.getAllTask.observe(this, Observer {
            when(it){
                is TaskResponce.Loading ->{
                    dataBinding.progressBar.visibility = View.VISIBLE
                }
                is TaskResponce.Successful ->{
                    dataBinding.progressBar.visibility = View.GONE
                    taskAdapter.submitList(it.data)
                    dataBinding.recyclerView.apply {
                        adapter = taskAdapter
                        layoutManager = LinearLayoutManager(this@MainActivity)
                    }
                }
                is TaskResponce.Error ->{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initializeViewModel() {
        val dataBaseInstance = TaskDataBase.getDataBaseInstance(this).getTaskDao()
        taskViewModel = ViewModelProvider(this, TaskViewModelFactory(Repository(dataBaseInstance)))[TaskViewModel::class.java]
    }
}