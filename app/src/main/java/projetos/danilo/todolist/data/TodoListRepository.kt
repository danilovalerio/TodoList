package projetos.danilo.todolist.data

import androidx.lifecycle.LiveData
import projetos.danilo.todolist.model.Task

class TodoListRepository(private val todoListDao: TodoListDao) {

    val getList: LiveData<List<Task>> = todoListDao.getList()

    suspend fun insertTask(task: Task) {
        todoListDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        todoListDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        todoListDao.deleteTask(task)
    }

    fun getTaskById(id: Long): LiveData<Task?>{
        return todoListDao.getTaskById(id)
    }

    fun searchByDateQuery(searchDate: String): LiveData<List<Task>> {
        return todoListDao.searchDataBase(searchDate)
    }
}