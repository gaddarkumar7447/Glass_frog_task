package com.example.glasstask.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.example.shared.Greeting
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityMainBinding

    private lateinit var taskViewModel: TaskViewModel

    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setContent {

            MainScreen(Greeting().greet())
        }

        //initializeViewModel()

        //taskViewModel.insertTask(TaskItem(1, "Gaddar kumar", "This is gaddar", System.currentTimeMillis(), false))

        /*dataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
        taskAdapter = TaskAdapter()
        initializeViewModel()

        dataBinding.addItemButton.setOnClickListener {
            insertTask()
        }

        fetchTaskData()

        adapterMethod()*/

    }


    private fun adapterMethod() {
        taskAdapter.setOnItemClickListener(object : TaskAdapter.OnClickListener {
            override fun onClickDeleteItem(taskItem: TaskItem) {
                taskViewModel.deleteTask(taskItem)
            }

            override fun onUpdateItem(taskItem: TaskItem) {
                val title = taskItem.title
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
                builder.setNegativeButton("Cancel") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }

                val alertDialog = builder.create()
                alertDialog.show()
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                    if (titleUpdate.text.isNotEmpty() && desUpdate.text.isNotEmpty()) {
                        if (desUpdate.text.toString() != des) {
                            taskViewModel.updateTask(
                                TaskItem(
                                    id,
                                    titleUpdate.text.toString(),
                                    desUpdate.text.toString(),
                                    System.currentTimeMillis()
                                )
                            )
                            alertDialog.dismiss()
                        } else {
                            alertDialog.dismiss()
                        }
                    } else {
                        showToast("fill all details")
                    }
                }
            }

            override fun taskDone(isChecked: Boolean, currentItem: TaskItem) {
                val title = currentItem.title
                val des = currentItem.description
                val id = currentItem.id

                taskViewModel.updateTask(
                    TaskItem(
                        id,
                        title,
                        des,
                        System.currentTimeMillis(),
                        isChecked
                    )
                )
                if (isChecked) {
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
                layoutManager = StaggeredGridLayoutManager(
                    2,
                    LinearLayoutManager.VERTICAL
                )/*LinearLayoutManager(this@MainActivity)*/
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
                taskViewModel.insertTask(
                    TaskItem(
                        0,
                        title.text.toString(),
                        des.text.toString(),
                        System.currentTimeMillis()
                    )
                )
                /*dataBinding.root.hideKeyboard()*/
                alertDialog.cancel()
            } else {
                showToast("fill all details")
            }
        }
    }

    private fun initializeViewModel() {
        val instance = TaskDataBase.getDataBaseInstance(this).getTaskDao()
        taskViewModel = ViewModelProvider(
            this,
            TaskViewModelFactory(Repository(instance))
        )[TaskViewModel::class.java]
    }

    @Composable
    fun MainScreen(greet: String) {
        val taskViewModel: TaskViewModel = hiltViewModel()
        val data = taskViewModel.getAllTask().observeAsState().value

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            floatingActionButton = {
                Box(
                    modifier = Modifier
                        .padding(end = 15.dp, bottom = 15.dp)
                        .size(50.dp)
                        .background(color = Color.Gray, shape = CircleShape)
                        .clip(CircleShape)
                        .clickable {

                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                Text(text = greet)

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                ) {
                    if (data != null) {
                        items(data) {
                            ShowDataInCard(it)
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun ShowDataInCard(taskItem: TaskItem) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        tonalElevation = 5.dp
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(text = taskItem.title)

            Text(text = taskItem.description)
        }
    }

    Spacer(modifier = Modifier.height(5.dp))
}