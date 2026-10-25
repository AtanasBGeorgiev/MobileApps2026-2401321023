package bg.uni_plovdiv.fmi.ag23.taskscheduler.ui.select

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bg.uni_plovdiv.fmi.ag23.taskscheduler.R
import bg.uni_plovdiv.fmi.ag23.taskscheduler.viewmodel.TaskSchedulerViewModel

class SelectTasksFragment : Fragment(R.layout.fragment_select_tasks) {
    private lateinit var viewModel: TaskSchedulerViewModel
    private lateinit var adapter: TaskSchedulerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewTasks)
        adapter = TaskSchedulerAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this)[TaskSchedulerViewModel::class.java]
        viewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            tasks?.let { adapter.setTasks(it) }
        }
    }
}
