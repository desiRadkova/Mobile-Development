package com.example.exampreparation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.exampreparation.databinding.ActivityCreateNewTaskBinding
import com.example.exampreparation.utility.SharedPrefsHelper
import com.google.android.material.snackbar.Snackbar

class CreateNewTaskActivity:AppCompatActivity() {
    private lateinit var binding: ActivityCreateNewTaskBinding
    override fun onCreate(savedInstenceState: Bundle?) {
        super.onCreate(savedInstenceState)

        binding = ActivityCreateNewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setDataBinding()
    }
    private fun setDataBinding() {
        binding.btnSaveTask.setOnClickListener {
            val taskName = binding.etTaskName.text.toString()
            if (taskName.isEmpty()) {
                Snackbar.make(binding.root,"Task name is empty", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (SharedPrefsHelper.taskExists (taskName)) {
                Snackbar.make(
                    binding.root,
                    "Task with that name already exists", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

        val taskDescription = binding.etTaskDescription.text.toString()
        SharedPrefsHelper.saveTask(taskName, taskDescription)
            finish()
}
    }
}