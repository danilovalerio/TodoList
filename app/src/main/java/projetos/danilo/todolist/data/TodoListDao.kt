package projetos.danilo.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.*
import projetos.danilo.todolist.model.Task
import projetos.danilo.todolist.utils.Constants

@Dao
interface TodoListDao {

    @Query("SELECT * FROM todolist_table ORDER BY id ASC")
    fun getList(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

//    @Query("SELECT * FROM todolist_table WHERE todolist_table.id == :id LIMIT 1")
    @Query("SELECT * FROM todolist_table WHERE id = :id")
    fun getTaskById(id: Long): LiveData<Task?>


}