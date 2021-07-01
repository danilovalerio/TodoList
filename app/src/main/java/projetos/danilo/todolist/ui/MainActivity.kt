package projetos.danilo.todolist.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import projetos.danilo.todolist.data.TodoViewModel
import projetos.danilo.todolist.databinding.ActivityMainBinding
import projetos.danilo.todolist.model.DateFilter
import projetos.danilo.todolist.model.Task

class MainActivity : AppCompatActivity() {

    lateinit var mTodoViewModel: TodoViewModel

    private lateinit var binding: ActivityMainBinding
    private val adapterTasks by lazy { TaskListAdapter() }
    private val adapterFilter by lazy { DatesFilterListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTasks.adapter = adapterTasks
        binding.rvDaysFilter.adapter = adapterFilter

        mTodoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        mTodoViewModel.getAllData.observe(this, Observer {
            updateList(it)
        })

        mTodoViewModel.filterDates.observe(this, Observer {
            updateDatesFilter(it)
        })
    }

    private fun updateList(list: MutableList<Task>) {
        if (list.isEmpty()){
            binding.includeState.emptyState.visibility = View.VISIBLE
        } else {
            binding.includeState.emptyState.visibility = View.GONE
        }

        adapterTasks.submitList(list)
        adapterTasks.notifyDataSetChanged()
    }

    private fun updateDatesFilter(list: MutableList<DateFilter>) {
        adapterFilter.submitList(list)
        adapterFilter.notifyDataSetChanged()
    }

    private fun setupListeners() {
        binding.fabAdd.setOnClickListener{
            startActivityForResult(
                Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK
            )
        }

        adapterTasks.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
        }

        adapterTasks.listenerDelete = {
            mTodoViewModel.deleteTask(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) {
            updateList(mTodoViewModel.getAllData.value as MutableList<Task>)
            updateDatesFilter(mTodoViewModel.updateDatesFilter())
        }
    }

    companion object {
        private const val CREATE_NEW_TASK = 1000
    }
}