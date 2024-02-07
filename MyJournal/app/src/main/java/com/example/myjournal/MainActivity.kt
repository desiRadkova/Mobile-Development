package com.example.myjournal

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.example.myjournal.databinding.ActivityMainBinding
import com.example.myjournal.utility.Constants
import com.example.myjournal.utility.SharedPrefsHelper
import com.google.android.material.snackbar.Snackbar

class MainActivity() : AppCompatActivity(), SensorEventListener{
    private lateinit var binding: ActivityMainBinding

    private var etJournalText: EditText? = null
    private var lastNoteText: String? = ""
    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null
    private val gravityForce = 9.8

    constructor(parcel: Parcel) : this() {
        lastNoteText = parcel.readString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        etJournalText = findViewById(R.id.et_journal_text)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        binding.btnSaveJournal.setEnabled(false) // set Save Journal Button inactive
        binding.btnLoadJournal.setEnabled(false) //set Load Button to be inactive

//The functionality for the active inactive Save Journey Button
        binding.etJournalText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                // TODO Auto-generated method stub
                if (s.toString() == "") {
                    binding.btnSaveJournal.setEnabled(false) //Save Journal Button inactive when no text in text filed
                } else {
                    binding.btnSaveJournal.setEnabled(true) // Save Journal Button is active when there is a text in text field
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
        // Functionality for active/inactive Load Button
        binding.etJournalIdText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                // TODO Auto-generated method stub
                if (s.toString() == "") {
                    binding.btnLoadJournal.setEnabled(false) //Load Journal Button inactive when no text in text field
                } else {
                    binding.btnLoadJournal.setEnabled(true) // Load Journal Button is active when there is a text in text field
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


        setContentView(binding.root)
        SharedPrefsHelper.init(this)
        setDataBinding()
        showSavedJorney()
    }
    private fun setDataBinding() {
        binding.btnSaveJournal.setOnClickListener{
            val journeyText = binding.etJournalText.text.toString()
            if (journeyText.isEmpty()) {
                Snackbar.make(binding.root,"Journey is empty", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (SharedPrefsHelper.journeyExists (journeyText)) {
                Snackbar.make(
                    binding.root,
                    "This Journey already exists", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            SharedPrefsHelper.saveJourney(journeyText)
        }
    }
    private fun showSavedJorney() {
        val journeys = SharedPrefsHelper.getAllJorneys()


        binding.btnLoadJournal.setEnabled(journeys.isNotEmpty())
        binding.btnLoadJournal.setOnClickListener{
            val journeyID = binding.etJournalIdText.text.toString().toInt()-1
            val journeyShow= SharedPrefsHelper.getJourneyByIndex(journeyID)
            val (a, b) = journeyShow //Take the first element from Pair of List
            if (journeyShow == null ){
                binding.journalShow.text = "No entry present"
            }
            binding.journalShow.text = a//journeyShow.toString()
            return@setOnClickListener
    }
}
    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }
    override fun onSensorChanged(event: SensorEvent?) {

        val y = event!!.values[1]
        Log.d("ACCELEROMETER_SENSOR_DATA", "Y: $y")

        if (y < (gravityForce / 2)) {
            binding.etJournalText?.setText("")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

}