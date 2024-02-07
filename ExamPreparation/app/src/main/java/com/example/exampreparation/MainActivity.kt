package com.example.exampreparation

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exampreparation.databinding.ActivityMainBinding
import com.example.exampreparation.utility.Constants
import com.example.exampreparation.utility.SharedPrefsHelper

class MainActivity : AppCompatActivity(), SensorEventListener {


    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SharedPrefsHelper.init(this)
        setDataBinding()
        showSavedTasks()
    }

    override fun onResume() {
        super.onResume()
        showSavedTasks()
    }

    private fun setDataBinding() {
        binding.btnCreateNewTask.setOnClickListener {
            val intent = Intent(this, CreateNewTaskActivity::class.java)
            startActivity(intent)
            binding.btnConfirm.setOnClickListener {

                val taskIndex =
                    binding.etUserChoice.text.toString().toInt() - 1
                val task = SharedPrefsHelper.getTaskWithIndex(taskIndex)
                val intent = Intent(this, ReviewTaskActivity::class.java)
                intent.putExtra(Constants.KEY_REVIEW_TASK_NAME, task.first)
                intent.putExtra(Constants.KEY_REVIEW_TASK_DESCRIPTION, task.second as String)
                startActivity(intent)
            }
        }
    }

    private fun showSavedTasks() {
        val tasks = SharedPrefsHelper.getAllTasks()
        if (tasks.isEmpty()) {
            binding.tvTasks.text = "No saved tasks available."
        }
        binding.tvTasks.text =
            tasks.mapIndexed { index, task -> "${index + 1}. $task" }.joinToString("\n")
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        TODO("Not yet implemented")
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }
}