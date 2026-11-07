package bg.uni_plovdiv.fmi.ag23.taskscheduler.notification

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import bg.uni_plovdiv.fmi.ag23.taskscheduler.data.entity.Task
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

object TaskNotificationScheduler {

    fun scheduleNotification(context: Context, task: Task) {
        val taskTimeMillis = parseTaskDateMillis(task.date) ?: return
        val notificationTriggerTime = taskTimeMillis - (30 * 60 * 1000) // 30 minutes before
        val delayMillis = notificationTriggerTime - System.currentTimeMillis()

        val workManager = WorkManager.getInstance(context)
        val workName = "task_notification_${task.id}"

        if (delayMillis > 0) {
            val inputData = workDataOf(
                "task_id" to task.id,
                "task_name" to task.name,
                "task_desc" to task.description
            )

            val workRequest = OneTimeWorkRequestBuilder<TaskNotificationWorker>()
                .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
                .setInputData(inputData)
                .build()

            workManager.enqueueUniqueWork(
                workName,
                ExistingWorkPolicy.REPLACE,
                workRequest
            )
        } else {
            workManager.cancelUniqueWork(workName)
        }
    }

    fun cancelNotification(context: Context, taskId: Long) {
        val workManager = WorkManager.getInstance(context)
        workManager.cancelUniqueWork("task_notification_$taskId")
    }

    private fun parseTaskDateMillis(dateStr: String): Long? {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            sdf.parse(dateStr)?.time
        } catch (e: Exception) {
            null
        }
    }
}
