package bg.uni_plovdiv.fmi.ag23.taskscheduler.ui.insert

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import bg.uni_plovdiv.fmi.ag23.taskscheduler.R
import bg.uni_plovdiv.fmi.ag23.taskscheduler.data.entity.Task
import bg.uni_plovdiv.fmi.ag23.taskscheduler.validation.ValidationRule
import bg.uni_plovdiv.fmi.ag23.taskscheduler.viewmodel.TaskSchedulerViewModel
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import java.util.Calendar
import java.util.Locale

class InsertTaskFragment : Fragment(R.layout.fragment_insert_task) {
    private lateinit var viewModel: TaskSchedulerViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TaskSchedulerViewModel::class.java]

        val editName = view.findViewById<EditText>(R.id.editName)
        val editDesc = view.findViewById<EditText>(R.id.editDesc)
        val editDate = view.findViewById<EditText>(R.id.editDate)
        editDate.isFocusable = false
        editDate.isClickable = true

        editDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->

                    // User chooses the date
                    calendar.set(year, month, dayOfMonth)

                    TimePickerDialog(
                        requireContext(),
                        { _, hourOfDay, minute ->

                            // User chooses the time
                            val selectedDateTime = String.format(
                                Locale.getDefault(),
                                "%04d-%02d-%02d %02d:%02d",
                                year,
                                month + 1,
                                dayOfMonth,
                                hourOfDay,
                                minute
                            )

                            editDate.setText(selectedDateTime)
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true // 24-hour format
                    ).show()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        val btnSave = view.findViewById<Button>(R.id.btnSave)

        val nameRule = ValidationRule(Regex("^[a-zA-Zа-яА-Я\\s]{2,50}$"), "Invalid name (2-50 chars)")

        btnSave.setOnClickListener {
            val name = editName.text.toString()
            val desc = editDesc.text.toString()
            val date = editDate.text.toString()

            if (!nameRule.validate(name)) {
                editName.error = nameRule.errorMessage
                return@setOnClickListener
            }

            if (name.isBlank() || date.isBlank()) {
                Toast.makeText(requireContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val task = Task(name = name, description = desc, date = date)
            viewModel.insert(task)
            Toast.makeText(requireContext(), "Task added successfully", Toast.LENGTH_SHORT).show()
            editName.text.clear()
            editDesc.text.clear()
            editDate.text.clear()
        }
    }
}
