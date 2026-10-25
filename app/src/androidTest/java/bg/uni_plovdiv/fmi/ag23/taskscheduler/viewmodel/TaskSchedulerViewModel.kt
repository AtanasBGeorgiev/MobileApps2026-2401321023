package bg.uni_plovdiv.fmi.ag23.taskscheduler.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import bg.uni_plovdiv.fmi.ag23.taskscheduler.data.TaskSchedulerDatabase
import bg.uni_plovdiv.fmi.ag23.taskscheduler.data.entity.Task
import bg.uni_plovdiv.fmi.ag23.taskscheduler.data.repository.TaskSchedulerRepository
import bg.uni_plovdiv.fmi.ag23.taskscheduler.data.repository.TaskSchedulerFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskSchedulerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TaskSchedulerRepository
    val allTasks: LiveData<List<Task>>

    init {
        val taskDao = TaskSchedulerDatabase.getDatabase(application).taskDao()
        repository = TaskSchedulerRepository(taskDao)
        allTasks = repository.allTasks
    }

    fun insert(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(task)
    }

    fun filter(filter: TaskSchedulerFilter): LiveData<List<Task>> {
        return repository.search(filter)
    }
}
