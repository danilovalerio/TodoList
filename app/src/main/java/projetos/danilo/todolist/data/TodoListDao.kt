package projetos.danilo.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.*
import projetos.danilo.todolist.model.Task
import projetos.danilo.todolist.utils.Constants

@Dao
interface TodoListDao {

    @Query("SELECT * FROM todolist_table ORDER BY date ASC")
    fun getList(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM todolist_table WHERE id = :id")
    fun getTaskById(id: Long): LiveData<Task?>

    @Query("SELECT * FROM todolist_table WHERE date LIKE :searchQuery")
    fun searchDataBase(searchQuery: String): LiveData<List<Task>>

}