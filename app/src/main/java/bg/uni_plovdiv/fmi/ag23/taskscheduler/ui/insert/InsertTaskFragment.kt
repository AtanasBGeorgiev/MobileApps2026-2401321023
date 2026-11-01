package bg.uni_plovdiv.fmi.ag23.taskscheduler.ui.insert

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import bg.uni_plovdiv.fmi.ag23.taskscheduler.R
import bg.uni_plovdiv.fmi.ag23.taskscheduler.data.entity.Task
import bg.uni_plovdiv.fmi.ag23.taskscheduler.validation.ValidationRule
import bg.uni_plovdiv.fmi.ag23.taskscheduler.viewmodel.TaskSchedulerViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

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
            showDateTimePicker { selectedDateTime ->
                editDate.setText(selectedDateTime)
            }
        }
        val btnSave = view.findViewById<MaterialButton>(R.id.btnSave)

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
                Snackbar.make(view, "Please fill all required fields", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val task = Task(name = name, description = desc, date = date)
            viewModel.insert(task)
            Snackbar.make(view, "Task added successfully", Snackbar.LENGTH_SHORT).show()
            editName.text.clear()
            editDesc.text.clear()
            editDate.text.clear()
        }
    }

    private fun showDateTimePicker(onDateTimeSelected: (String) -> Unit) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = selection

            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Time")
                .build()

            timePicker.addOnPositiveButtonClickListener {
                val selectedDateTime = String.format(
                    Locale.getDefault(),
                    "%04d-%02d-%02d %02d:%02d",
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH),
                    timePicker.hour,
                    timePicker.minute
                )
                onDateTimeSelected(selectedDateTime)
            }
            timePicker.show(childFragmentManager, "MATERIAL_TIME_PICKER")
        }
        datePicker.show(childFragmentManager, "MATERIAL_DATE_PICKER")
    }
}
