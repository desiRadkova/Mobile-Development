package com.example.regularexam.utility

import android.content.Context
import android.content.SharedPreferences

object SharedPrefsHelper {
    const val KEY_SHARED_PREFS_NAME = "KEY_SHARED_PREFS_NAME"

    private lateinit var sharedPreferences: SharedPreferences
    fun init(context: Context) {
        sharedPreferences =
            context.getSharedPreferences (KEY_SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }
    fun noteExists (noteText: String): Boolean {
        return sharedPreferences.contains(noteText)
    }
    fun saveNote(noteText: String): Boolean {
        val editor = sharedPreferences.edit()
        editor.putString(noteText,noteText)//putString (noteText)
        return editor.commit()
    }
    fun getAllNotes():List<String>{
        return sharedPreferences.all.map { it.key }
    }
    fun getNoteWithIndex (index: Int): Pair<String, *> {
        return sharedPreferences.all.toList() [index]
    }
    fun deleteNote(noteText: String): Boolean {
        val editor = sharedPreferences.edit()
        editor.remove(noteText)
        return editor.commit()
    }
}