package projetos.danilo.todolist.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import projetos.danilo.todolist.extensions.dayOfWeekBR
import projetos.danilo.todolist.model.DateFilter
import projetos.danilo.todolist.model.Task
import java.util.*

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDataSource = TaskDataSource

    val getAllData: MutableLiveData<MutableList<Task>> by lazy {
        MutableLiveData<MutableList<Task>>()
    }
//    var allData = getAllData

    val filterDates: MutableLiveData<MutableList<DateFilter>> by lazy {
        MutableLiveData<MutableList<DateFilter>>()
    }

//    var datesOfFilter = filterDates

    init {
        updateAllData()
        updateDatesFilter()
    }

    fun inserTask(task: Task) {
        TaskDataSource.insertTask(task)
        updateAllData()
    }

    fun deleteTask(task: Task) {
        TaskDataSource.delete(task)
        updateAllData()
        updateDatesFilter()
    }

    fun findViewById(id: Int): Task? {
        return TaskDataSource.findById(id)
    }

    private fun updateAllData() {
        getAllData.postValue(TaskDataSource.getList())
        filterDates.postValue(updateDatesFilter())
    }

    fun updateDatesFilter(): MutableList<DateFilter> {
        val listFilter = TaskDataSource.getList().distinctBy { it.date }
        val dates: MutableList<DateFilter> = mutableListOf()

        listFilter?.forEach {
            if (it.date.isNotEmpty()) {
                val day = it.date.take(2).toInt()
                val month = it.date.substring(3, 5).toInt()
                val year = it.date.substring(6, 10).toInt()
                val dayOfWeek = Date().dayOfWeekBR(it.date)
                dates.add(DateFilter(day, month, year, dayOfWeek))
            }
        }

        return dates
    }

}