package bg.uni_plovdiv.fmi.ag23.taskscheduler.data.repository

import androidx.lifecycle.LiveData
import bg.uni_plovdiv.fmi.ag23.taskscheduler.data.dao.TaskDao
import bg.uni_plovdiv.fmi.ag23.taskscheduler.data.entity.Task

class TaskSchedulerRepository(private val taskDao: TaskDao) {
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }

    fun search(filter: TaskSchedulerFilter): LiveData<List<Task>> {
        return if (filter.query.isBlank()) {
            taskDao.getAllTasks()
        } else {
            taskDao.searchTasks(filter.query)
        }
    }
}