package bg.uni_plovdiv.fmi.ag23.taskscheduler.ui.select

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bg.uni_plovdiv.fmi.ag23.taskscheduler.R
import bg.uni_plovdiv.fmi.ag23.taskscheduler.data.entity.Task
import com.google.android.material.button.MaterialButton

class TaskSchedulerAdapter(
    private val onEditClick: ((Task) -> Unit)? = null,
    private val onDeleteClick: ((Task) -> Unit)? = null
) : RecyclerView.Adapter<TaskSchedulerAdapter.TaskViewHolder>() {
    private var tasks = emptyList<Task>()

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textContactName)
        val descTextView: TextView = itemView.findViewById(R.id.textContactDesc)
        val dateTextView: TextView = itemView.findViewById(R.id.textContactDate)
        val editButton: MaterialButton = itemView.findViewById(R.id.btnEditTask)
        val deleteButton: MaterialButton = itemView.findViewById(R.id.btnDeleteTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = tasks[position]
        holder.nameTextView.text = current.name
        holder.descTextView.text = current.description
        holder.dateTextView.text = current.date

        holder.editButton.setOnClickListener {
            onEditClick?.invoke(current)
        }
        holder.deleteButton.setOnClickListener {
            onDeleteClick?.invoke(current)
        }
    }

    override fun getItemCount() = tasks.size

    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }
}
