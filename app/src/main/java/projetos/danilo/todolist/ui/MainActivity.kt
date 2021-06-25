package projetos.danilo.todolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import projetos.danilo.todolist.data.TaskDataSource
import projetos.danilo.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapterTasks by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupRecyclerView()


    }

    private fun setupRecyclerView() {
        binding.rvTasks.adapter = adapterTasks
        updateList()
    }

    private fun updateList() {
        adapterTasks.submitList(TaskDataSource.getList())
    }

    private fun setupListeners() {
        binding.fabAdd.setOnClickListener{
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }

        adapterTasks.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
        }

        adapterTasks.listenerDelete = {
            Log.e("TAG", "listernerDelete $it")
            TaskDataSource.delete(it)
            updateList()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) {
            updateList()
        }
    }

    companion object {
        private const val CREATE_NEW_TASK = 1000
    }
}