package com.example.myjournal.utility

import android.content.Context
import android.content.SharedPreferences

object SharedPrefsHelper {
    const val KEY_SHARED_PREFS_NAME = "KEY_SHARED_PREFS_NAME"

    private lateinit var sharedPreferences: SharedPreferences
    fun init(context: Context) {
        sharedPreferences =
            context.getSharedPreferences (KEY_SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }
    fun journeyExists (noteText: String): Boolean {
        return sharedPreferences.contains(noteText)
    }
    fun saveJourney(noteText: String): Boolean {
        val editor = sharedPreferences.edit()
        editor.putString(noteText,noteText)//putString (noteText)
        return editor.commit()
    }
    fun getAllJorneys():List<String>{
        return sharedPreferences.all.map { it.key }
    }
    fun getJourneyByIndex (index: Int): Pair<String, *> {
        return sharedPreferences.all.toList().asReversed() [index]
    }
    fun deleteJourney(noteText: String): Boolean {
        val editor = sharedPreferences.edit()
        editor.remove(noteText)
        return editor.commit()
    }
}