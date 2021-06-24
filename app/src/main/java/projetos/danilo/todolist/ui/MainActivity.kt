package projetos.danilo.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import projetos.danilo.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()


    }

    private fun setupListeners() {
        binding.fabAdd.setOnClickListener{
            startActivity(Intent(this, AddTaskActivity::class.java))
        }
    }
}