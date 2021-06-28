package projetos.danilo.todolist.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import projetos.danilo.todolist.model.Task

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDataSource = TaskDataSource

    val getAllData: MutableLiveData<MutableList<Task>> by lazy {
        MutableLiveData<MutableList<Task>>()
    }

    init {
        updateAllData()
    }

    fun inserTask(task: Task) {
        TaskDataSource.insertTask(task)
        updateAllData()
    }

    fun deleteTask(task: Task) {
        TaskDataSource.delete(task)
        updateAllData()
    }

    fun findViewById(id: Int): Task? {
        return TaskDataSource.findById(id)
    }

    private fun updateAllData() {
        getAllData.postValue(TaskDataSource.getList())
    }

}