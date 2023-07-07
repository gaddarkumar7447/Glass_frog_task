package com.example.glasstask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.glasstask.databinding.TaskviewitemlayoutBinding
import com.example.glasstask.model.TaskItem


class TaskAdapter : ListAdapter<TaskItem, TaskAdapter.TaskViewHolder>(diffCallback) {

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<TaskItem?>() {
            override fun areItemsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(TaskviewitemlayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.taskViewItemBinding.apply {
            title.text = currentItem.title
            description.text = currentItem.description
        }
    }

    class TaskViewHolder(val taskViewItemBinding: TaskviewitemlayoutBinding) : RecyclerView.ViewHolder(taskViewItemBinding.root)
}