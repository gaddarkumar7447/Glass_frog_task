package com.example.glasstask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.glasstask.databinding.TaskviewitemlayoutBinding
import com.example.glasstask.model.TaskItem
import com.example.glasstask.utility.formatTimestamp


class TaskAdapter : ListAdapter<TaskItem, TaskAdapter.TaskViewHolder>(diffCallback) {

    private var onItemClickListener: OnClickListener? = null

    companion object {
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
            timeShow.text = formatTimestamp(currentItem.taskCreateTime)

            checkbox.isChecked = currentItem.isComplete

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                currentItem.isComplete = isChecked

                onItemClickListener?.taskDone(isChecked, currentItem)
            }
        }

        holder.taskViewItemBinding.deleteTask.setOnClickListener {
            onItemClickListener?.onClickDeleteItem(currentItem)
        }

        holder.taskViewItemBinding.itemCard.setOnClickListener {
            onItemClickListener?.onUpdateItem(currentItem)
        }


    }

    fun setOnItemClickListener(listener: OnClickListener) {
        onItemClickListener = listener
    }

    interface OnClickListener {
        fun onClickDeleteItem(taskItem: TaskItem)
        fun onUpdateItem(taskItem: TaskItem)
        fun taskDone(isChecked: Boolean, currentItem: TaskItem)
    }

    class TaskViewHolder(val taskViewItemBinding: TaskviewitemlayoutBinding) : RecyclerView.ViewHolder(taskViewItemBinding.root)
}