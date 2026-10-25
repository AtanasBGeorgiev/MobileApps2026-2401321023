package bg.uni_plovdiv.fmi.ag23.taskscheduler.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import bg.uni_plovdiv.fmi.ag23.taskscheduler.data.dao.TaskDao
import bg.uni_plovdiv.fmi.ag23.taskscheduler.data.entity.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskSchedulerDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskSchedulerDatabase? = null

        fun getDatabase(context: Context): TaskSchedulerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskSchedulerDatabase::class.java,
                    "task_scheduler_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
