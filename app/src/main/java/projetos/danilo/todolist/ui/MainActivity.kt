package projetos.danilo.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


    }

    private fun setupListeners() {
        binding.fabAdd.setOnClickListener{
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CREATE_NEW_TASK) {
            binding.rvTasks.adapter = adapterTasks
            adapterTasks.submitList(TaskDataSource.getList())
        }
    }

    companion object {
        private const val CREATE_NEW_TASK = 1000
    }
}