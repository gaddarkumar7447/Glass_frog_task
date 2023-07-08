package com.example.glasstask.activity

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.glasstask.R
import com.example.glasstask.adapter.TaskAdapter
import com.example.glasstask.database.TaskDataBase
import com.example.glasstask.databinding.ActivityMainBinding
import com.example.glasstask.databinding.TakeinputlayoutBinding
import com.example.glasstask.model.TaskItem
import com.example.glasstask.repo.Repository
import com.example.glasstask.utility.showToast
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
        taskAdapter = TaskAdapter()
        initializeViewModel()

        dataBinding.addItemButton.setOnClickListener {
            insertTask()
        }

        fetchTaskData()

        adapterMethod()
    }



    private fun adapterMethod() {
        taskAdapter.setOnItemClickListener(object : TaskAdapter.OnClickListener {
            override fun onClickDeleteItem(taskItem: TaskItem) {
                taskViewModel.deleteTask(taskItem)
            }

            override fun onUpdateItem(taskItem: TaskItem) {
                val title  = taskItem.title
                val des = taskItem.description
                val id = taskItem.id
                val builder = AlertDialog.Builder(this@MainActivity, R.style.CustomAlertDialogStyle)
                builder.setTitle("Update Task details")
                val dialogLayout = TakeinputlayoutBinding.inflate(LayoutInflater.from(this@MainActivity))

                val titleUpdate = dialogLayout.taskTitle
                val desUpdate = dialogLayout.taskDescription

                titleUpdate.setText(title)
                desUpdate.setText(des)

                builder.setView(dialogLayout.root)
                builder.setCancelable(false)
                builder.setPositiveButton("OK") { dialogInterface, i -> }
                builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })

                val alertDialog = builder.create()
                alertDialog.show()
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                    if (titleUpdate.text.isNotEmpty() && desUpdate.text.isNotEmpty()){
                        if (desUpdate.text.toString() != des){
                            taskViewModel.updateTask(TaskItem(id,titleUpdate.text.toString() , desUpdate.text.toString(), System.currentTimeMillis()))
                            alertDialog.dismiss()
                        }else{
                            alertDialog.dismiss()
                        }
                    }else{
                        showToast("fill all details")
                    }
                }
            }

            override fun taskDone(isChecked: Boolean, currentItem: TaskItem) {
                val title  = currentItem.title
                val des = currentItem.description
                val id = currentItem.id

                taskViewModel.updateTask(TaskItem(id, title, des, System.currentTimeMillis(), isChecked))
                if (isChecked){
                    showToast("$title Done \uD83D\uDE0D")
                }
            }
        })
    }

    private fun fetchTaskData() {
        taskViewModel.getAllTask().observe(this, Observer {
            taskAdapter.submitList(it)
            dataBinding.recyclerView.apply {
                adapter = taskAdapter
                layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)/*LinearLayoutManager(this@MainActivity)*/
            }
        })
    }

    private fun insertTask() {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialogStyle)
        builder.setTitle("Enter Task")
        val dialogLayout = TakeinputlayoutBinding.inflate(LayoutInflater.from(this))
        val title = dialogLayout.taskTitle
        val des = dialogLayout.taskDescription

        builder.setView(dialogLayout.root)
        builder.setCancelable(false)
        builder.setPositiveButton("OK") { _, _ -> }
        builder.setNegativeButton("Cancel") { dialogInterface, _ -> dialogInterface.dismiss() }

        val alertDialog = builder.create()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if (title.text.isNotEmpty() && des.text.isNotEmpty()) {
                taskViewModel.insertTask(TaskItem(0, title.text.toString(), des.text.toString(), System.currentTimeMillis()))
                /*dataBinding.root.hideKeyboard()*/
                alertDialog.cancel()
            } else {
               showToast("fill all details")
            }
        }
    }

    private fun initializeViewModel() {
        val instance = TaskDataBase.getDataBaseInstance(this).getTaskDao()
        taskViewModel = ViewModelProvider(this, TaskViewModelFactory(Repository(instance)))[TaskViewModel::class.java]
    }
}