package projetos.danilo.todolist.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import projetos.danilo.todolist.R
import projetos.danilo.todolist.data.TodoViewModel
import projetos.danilo.todolist.databinding.ActivityMainBinding
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
        visibleLoading()
        setupObservers()
    }

    private fun visibleLoading() {
        binding.includeStateLoading.clLoadingState.visibility = View.VISIBLE
    }

    private fun goneLoading() {
        binding.includeStateLoading.clLoadingState.visibility = View.GONE
    }

    private fun setupObservers() {
        mTodoViewModel.getAllData.observe(this, {
            updateList(it)
            binding.tvFilterActive.text = getString(R.string.label_all_tasks_by_date_desc)
            mTodoViewModel.updateFilterAllData()
            goneLoading()
        })

        mTodoViewModel.filterDates.observe(this, Observer {
            updateDatesFilter(it)
        })
    }

    private fun setupListeners() {
        binding.fabAdd.setOnClickListener {
            startActivityForResult(
                Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK
            )
        }

        adapterTasks.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, UPDATE_TASK)
        }

        adapterTasks.listenerDelete = {
            visibleLoading()
            mTodoViewModel.deleteTask(it)
        }

        adapterFilter.listenerClick = {
            visibleLoading()
            mTodoViewModel.searchByDate(it).observe(this, Observer { filteredList ->
                updateList(filteredList)
            })
            mTodoViewModel.updateFilterAllData()
            goneLoading()

            binding.tvFilterActive.text = getString(
                R.string.label_tasks_filtered_by_date,
                it
            )
            binding.btnCleanFilter.isEnabled = true
        }

        binding.btnCleanFilter.setOnClickListener {
            clearFilterAndUpdatList()
        }
    }

    private fun updateList(list: List<Task>) {
        if (list.isEmpty()) {
            binding.includeState.emptyState.visibility = View.VISIBLE
        } else {
            binding.includeState.emptyState.visibility = View.GONE
        }

        adapterTasks.submitList(list)
        adapterTasks.notifyDataSetChanged()
    }

    private fun updateDatesFilter(list: MutableList<String>) {
        adapterFilter.dateSelectedClean()
        adapterFilter.submitList(list)
        adapterFilter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        visibleLoading()

        when {
            requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK -> {
                feedBackTaskActions(resources.getString(R.string.label_new_task_success))
            }

            requestCode == UPDATE_TASK && resultCode == Activity.RESULT_OK -> {
                feedBackTaskActions(resources.getString(R.string.label_update_task_success))
            }

            else -> goneLoading()
        }
    }

    //Feedback actions
    private fun feedBackTaskActions(msg: String) {
        val snackbar = Snackbar.make(
            this, binding.tvTitle,
            msg, Snackbar.LENGTH_LONG
        )
        snackbar.show()

        clearFilterAndUpdatList()
    }

    private fun clearFilterAndUpdatList() {
        visibleLoading()
        updateList(mTodoViewModel.getList())
        mTodoViewModel.updateFilterAllData()
        goneLoading()
        binding.btnCleanFilter.isEnabled = false
        binding.tvFilterActive.text = getString(R.string.label_all_tasks_by_date_desc)
    }

    companion object {
        private const val CREATE_NEW_TASK = 1000
        private const val UPDATE_TASK = 1001
    }
}