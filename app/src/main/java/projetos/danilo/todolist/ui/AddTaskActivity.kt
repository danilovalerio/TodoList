package projetos.danilo.todolist.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
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

        if (intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID, 0)
            mTodoViewModel.findViewById(taskId)?.let {
                binding.tilTitle.text = it.title
                binding.tilDescription.text = it.description
                binding.tilDate.text = it.date
                binding.tilTimer.text = it.hour
            }
        }

        setupListeners()
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

            timePicker.addOnPositiveButtonClickListener{
                val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
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
                id = intent.getIntExtra(TASK_ID, 0)
            )

            mTodoViewModel.inserTask(task)

            setResult(Activity.RESULT_OK)
            finish()
        }

    }

    companion object {
        const val TASK_ID = "task_id"
    }
}