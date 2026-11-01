package bg.uni_plovdiv.fmi.ag23.taskscheduler.ui.delete

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import bg.uni_plovdiv.fmi.ag23.taskscheduler.R
import bg.uni_plovdiv.fmi.ag23.taskscheduler.data.entity.Task
import bg.uni_plovdiv.fmi.ag23.taskscheduler.viewmodel.TaskSchedulerViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

class DeleteTaskFragment : Fragment(R.layout.fragment_delete_task) {
    private lateinit var viewModel: TaskSchedulerViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TaskSchedulerViewModel::class.java]

        val textName = view.findViewById<TextView>(R.id.textDeleteName)
        val textDesc = view.findViewById<TextView>(R.id.textDeleteDesc)
        val textDate = view.findViewById<TextView>(R.id.textDeleteDate)
        val btnConfirm = view.findViewById<MaterialButton>(R.id.btnConfirmDelete)
        val btnCancel = view.findViewById<MaterialButton>(R.id.btnCancelDelete)

        val taskId = arguments?.getLong("taskId") ?: 0L
        val currentName = arguments?.getString("taskName") ?: ""
        val currentDesc = arguments?.getString("taskDesc") ?: ""
        val currentDate = arguments?.getString("taskDate") ?: ""

        textName.text = currentName
        textDesc.text = currentDesc
        textDate.text = currentDate

        btnConfirm.setOnClickListener {
            val taskToDelete = Task(id = taskId, name = currentName, description = currentDesc, date = currentDate)
            viewModel.delete(taskToDelete)
            Snackbar.make(view, "Task deleted successfully", Snackbar.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
