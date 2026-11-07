package bg.uni_plovdiv.fmi.ag23.taskscheduler.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update
import bg.uni_plovdiv.fmi.ag23.taskscheduler.data.entity.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY date ASC")
    fun getAllTasks(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Query("SELECT * FROM tasks WHERE name LIKE '%' || :query || '%'")
    fun searchTasks(query: String): LiveData<List<Task>>

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}