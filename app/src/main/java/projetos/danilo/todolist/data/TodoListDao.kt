package projetos.danilo.todolist.data

import androidx.lifecycle.LiveData
import projetos.danilo.todolist.model.Task

interface TodoListDao {

    fun getList(): LiveData<Task>

    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(task: Task)
}