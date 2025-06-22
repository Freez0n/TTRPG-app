package com.example.ttrpg.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ttrpg.data.Character
import com.example.ttrpg.data.PrefsRepository

class CharacterViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = PrefsRepository(app)

    private val _allCharacters = MutableLiveData<List<Character>>(repo.getAll())
    val allCharacters: LiveData<List<Character>> = _allCharacters

    fun addCharacter(ch: Character) {
        val updated = _allCharacters.value.orEmpty().toMutableList().apply { add(ch) }
        repo.saveAll(updated)
        _allCharacters.value = updated
    }

    fun deleteCharacter(ch: Character) {
        val updated = _allCharacters.value.orEmpty().toMutableList().apply { remove(ch) }
        repo.saveAll(updated)
        _allCharacters.value = updated
    }

    fun getCharacterById(id: Long): LiveData<Character?> {
        val result = MutableLiveData<Character?>()
        result.value = repo.getCharacterById(id)
        return result
    }
    fun updateCharacter(updated: Character) {
        val currentList = _allCharacters.value.orEmpty().toMutableList()
        val index = currentList.indexOfFirst { it.id == updated.id }
        if (index != -1) {
            currentList[index] = updated
            repo.saveAll(currentList)
            _allCharacters.value = currentList
        }
    }

}
