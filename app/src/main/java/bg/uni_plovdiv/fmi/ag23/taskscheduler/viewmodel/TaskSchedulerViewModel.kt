package bg.uni_plovdiv.fmi.ag23.taskscheduler.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import bg.uni_plovdiv.fmi.ag23.taskscheduler.data.TaskSchedulerDatabase
import bg.uni_plovdiv.fmi.ag23.taskscheduler.data.entity.Task
import bg.uni_plovdiv.fmi.ag23.taskscheduler.data.repository.TaskSchedulerFilter
import bg.uni_plovdiv.fmi.ag23.taskscheduler.data.repository.TaskSchedulerRepository
import bg.uni_plovdiv.fmi.ag23.taskscheduler.notification.TaskNotificationScheduler
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
        val id = repository.insert(task)
        val taskWithId = task.copy(id = id)
        TaskNotificationScheduler.scheduleNotification(getApplication(), taskWithId)
    }

    fun update(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(task)
        TaskNotificationScheduler.scheduleNotification(getApplication(), task)
    }

    fun delete(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(task)
        TaskNotificationScheduler.cancelNotification(getApplication(), task.id)
    }

    fun filter(filter: TaskSchedulerFilter): LiveData<List<Task>> {
        return repository.search(filter)
    }
}
