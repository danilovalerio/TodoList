package projetos.danilo.todolist.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import projetos.danilo.todolist.model.Task

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val todoListDao = TodoListDatabase.getDatabase(application).todoListDao()
    private val repository: TodoListRepository

    private val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    val getAllData: LiveData<List<Task>>

    val filterDates: MutableLiveData<MutableList<String>> by lazy {
        MutableLiveData<MutableList<String>>()
    }

    init {
        repository = TodoListRepository(todoListDao)
        getAllData = repository.getList
    }

    fun checkDatabaseEmpty(todoList: List<Task>) {
        emptyDatabase.value = todoList.isEmpty()
    }

    fun insertTask(task: Task) {
        if (task.id.toInt() == 0) {
            viewModelScope.launch(Dispatchers.IO) { repository.insertTask(task) }
        } else {
            updateTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) { repository.updateTask(task) }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) { repository.deleteTask(task) }
    }

    fun findTaskById(id: Long): Task? {
        getAllData.value?.forEach {
            if (it.id == id) {
                return it
            }
        }

        return null
    }

    fun searchByDate(date: String): LiveData<List<Task>> {
        return repository.searchByDateQuery(date)
    }

    fun updateFilterAllData() {
        filterDates.postValue(updateDatesFilter())
    }

    private fun updateDatesFilter(): MutableList<String> {
        val listFilter = getAllData.value?.distinctBy { it.date }
        val dates: MutableList<String> = mutableListOf()

        listFilter?.forEach {
            if (it.date.isNotEmpty()) {
                dates.add(it.date)
            }
        }

        return dates
    }

    fun getList(): List<Task> {
        return getAllData.value!!
    }

}