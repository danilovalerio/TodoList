package projetos.danilo.todolist.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import projetos.danilo.todolist.R
import projetos.danilo.todolist.data.TodoViewModel
import projetos.danilo.todolist.databinding.ActivityAddTaskBinding
import projetos.danilo.todolist.extensions.format
import projetos.danilo.todolist.extensions.text
import projetos.danilo.todolist.model.Task
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    lateinit var mTodoViewModel: TodoViewModel

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        mTodoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        setupListeners()

        val taskId = intent.getLongExtra(TASK_ID, 0)

        if (taskId != null && taskId > 0) {
            binding.btnNewTask.text = getString(R.string.label_button_update_task)
            binding.toolbar.title = getString(R.string.label_button_update_task)
        } else {
            binding.btnNewTask.text = getString(R.string.label_button_new_task)
            binding.toolbar.title = getString(R.string.label_button_new_task)
        }

        setupObservers(taskId)

//        if (intent.hasExtra(TASK_ID)) {
//            val taskId = intent.getLongExtra(TASK_ID, 0)
//
//            mTodoViewModel.findTaskById(taskId)?.let {
//                binding.tilTitle.text = it.title
//                binding.tilDescription.text = it.description
//                binding.tilDate.text = it.date
//                binding.tilTimer.text = it.hour
//            }
//        }
    }

    private fun setupListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
//                binding.tilDate.text = Date(it * offset).format()
                binding.tilDate.text = Date(it).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tilTimer.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timePicker.addOnPositiveButtonClickListener {
                val minute =
                    if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.tilTimer.text = "$hour:$minute"
            }

            timePicker.show(supportFragmentManager, null)
        }

        binding.btnEsc.setOnClickListener {
            finish()
        }

        binding.btnNewTask.setOnClickListener {
            val task = Task(
                title = binding.tilTitle.text,
                description = binding.tilDescription.text,
                hour = binding.tilTimer.text,
                date = binding.tilDate.text,
                id = intent.getLongExtra(TASK_ID, 0)
            )

            mTodoViewModel.insertTask(task)

            setResult(Activity.RESULT_OK)
            finish()
        }

    }

    private fun setupObservers(taskId: Long) {
        mTodoViewModel.getAllData.observe(this, {
            mTodoViewModel.checkDatabaseEmpty(it)
            Log.i("GET_ALL_DATA", "allData: $it")

            mTodoViewModel.findTaskById(taskId)?.let {
                binding.tilTitle.text = it.title
                binding.tilDescription.text = it.description
                binding.tilDate.text = it.date
                binding.tilTimer.text = it.hour
            }
        })
    }

    companion object {
        const val TASK_ID = "task_id"
    }
}