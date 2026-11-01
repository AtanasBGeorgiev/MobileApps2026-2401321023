package bg.uni_plovdiv.fmi.ag23.taskscheduler.ui.select

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bg.uni_plovdiv.fmi.ag23.taskscheduler.R
import bg.uni_plovdiv.fmi.ag23.taskscheduler.data.repository.TaskSchedulerFilter
import bg.uni_plovdiv.fmi.ag23.taskscheduler.viewmodel.TaskSchedulerViewModel

class SelectTasksFragment : Fragment(R.layout.fragment_select_tasks) {
    private lateinit var viewModel: TaskSchedulerViewModel
    private lateinit var adapter: TaskSchedulerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewTasks)
        val editSearch = view.findViewById<EditText>(R.id.editSearch)

        adapter = TaskSchedulerAdapter(
            onEditClick = { task ->
                val bundle = Bundle().apply {
                    putLong("taskId", task.id)
                    putString("taskName", task.name)
                    putString("taskDesc", task.description)
                    putString("taskDate", task.date)
                }
                findNavController().navigate(R.id.action_selectTasksFragment_to_updateTaskFragment, bundle)
            },
            onDeleteClick = { task ->
                val bundle = Bundle().apply {
                    putLong("taskId", task.id)
                    putString("taskName", task.name)
                    putString("taskDesc", task.description)
                    putString("taskDate", task.date)
                }
                findNavController().navigate(R.id.action_selectTasksFragment_to_deleteTaskFragment, bundle)
            }
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this)[TaskSchedulerViewModel::class.java]

        viewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            tasks?.let { adapter.setTasks(it) }
        }

        editSearch?.doOnTextChanged { text, _, _, _ ->
            val query = text?.toString().orEmpty()
            viewModel.filter(TaskSchedulerFilter(query = query)).observe(viewLifecycleOwner) { filteredTasks ->
                filteredTasks?.let { adapter.setTasks(it) }
            }
        }
    }
}
