package com.example.ttrpg.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrefsRepository(context: Context) {

    private val prefs = context.getSharedPreferences("ttrpg_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val type = object : TypeToken<List<Character>>() {}.type

    fun getAll(): List<Character> {
        val json = prefs.getString("chars", null) ?: return emptyList()
        return gson.fromJson(json, type)
    }

    fun saveAll(list: List<Character>) {
        prefs.edit()
            .putString("chars", gson.toJson(list))
            .apply()
    }

    fun getCharacterById(id: Long): Character? {
        return getAll().find { it.id.toLong() == id }
    }
}
