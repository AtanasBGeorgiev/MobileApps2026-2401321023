package bg.uni_plovdiv.fmi.ag23.taskscheduler.notification

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class TaskNotificationWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val taskId = inputData.getLong("task_id", 0L)
        val taskName = inputData.getString("task_name") ?: "Task"
        val taskDesc = inputData.getString("task_desc") ?: ""

        val message = if (taskDesc.isNotBlank()) {
            "Task starting in 30 minutes: $taskDesc"
        } else {
            "Task starting in 30 minutes!"
        }

        TaskNotificationHelper.showNotification(
            applicationContext,
            taskId.toInt(),
            taskName,
            message
        )

        return Result.success()
    }
}
