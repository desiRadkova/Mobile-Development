package com.example.regularexam

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.regularexam.databinding.ActivityMainBinding
import com.example.regularexam.utility.Constants
import com.example.regularexam.utility.SharedPrefsHelper
import com.google.android.material.snackbar.Snackbar
import android.hardware.SensorEvent as SensorEvent1


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.btnSave.setEnabled(false) // set button disable initially

        binding.etNote.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                // TODO Auto-generated method stub
                if (s.toString() == "") {
                    binding.btnSave.setEnabled(false)
                } else {
                    binding.btnSave.setEnabled(true)
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })
//The func with tilt detection

         fun onSensorChanged(event: SensorEvent1) {
             val note = intent.getStringExtra(Constants.KEY_REVIEW_NOTE)
             if (note == null) {
                 return Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_LONG).show()
             }
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                // Check the orientation based on accelerometer data
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                val isFacingUp = z > 9.0
                val isTiltOnY = y > 0.0

                if (isFacingUp && isTiltOnY) {
                    binding.etNote.text.clear()
                    SharedPrefsHelper.deleteNote(note)
                    finish()
                }
            }
        }
        setContentView(binding.root)
        SharedPrefsHelper.init(this)
        setDataBinding()
        showSavedNote()
    }
    private fun setDataBinding()
    {
        //binding.btnSave.setEnabled(!binding.etNote.text.toString().trim().equals(""))

        binding.btnSave.setOnClickListener{
            val noteText = binding.etNote.text.toString()
            if (noteText.isEmpty()) {
                Snackbar.make(binding.root,"Note is empty", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }


            if (SharedPrefsHelper.noteExists (noteText)) {
                Snackbar.make(
                    binding.root,
                    "This Note already exists", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            SharedPrefsHelper.saveNote(noteText)//saveTask(taskName, taskDescription)
            finish()
        }}
    @SuppressLint("SuspiciousIndentation")
    private fun showSavedNote(){
        val note = SharedPrefsHelper.getAllNotes()
        binding.btnShow.setEnabled(note.isNotEmpty())
        binding.btnShow.setOnClickListener{
        binding.tvShowNote.text = note.mapIndexed { index, task -> "${index + 1}. $task" } .joinToString("\n")
            return@setOnClickListener
    }
    //.text = tasks.mapIndexed { index, task -> "${index + 1}. $task" } .joinToString("\n")
    }


}