package com.example.exampreparation.utility

import android.content.Context
import android.content.SharedPreferences

object SharedPrefsHelper {
    const val KEY_SHARED_PREFS_NAME = "KEY_SHARED_PREFS_NAME"
    private lateinit var sharedPreferences: SharedPreferences
    fun init(context: Context) {
        sharedPreferences =
            context.getSharedPreferences (KEY_SHARED_PREFS_NAME, Context.MODE_PRIVATE)
}
    fun taskExists (name: String): Boolean {
    return sharedPreferences.contains(name)
    }

    fun saveTask(name: String, description: String): Boolean {
        val editor = sharedPreferences.edit()
        editor.putString (name, description)
       return editor.commit()
    }
    fun getAllTasks():List<String>{
        return sharedPreferences.all.map { it.key }
    }
    fun getTaskWithIndex (index: Int): Pair<String, *> {
    return sharedPreferences.all.toList() [index]
    }
    fun completeTask(name: String): Boolean {
        val editor = sharedPreferences.edit()
        editor.remove(name)
        return editor.commit()
    }
}