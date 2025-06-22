package com.example.ttrpg.data

import java.io.Serializable

data class Character(
    val id: Int = 0,

    val name: String,
    val race: String,
    val level: Int,
    val experience: Int,

    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,

    val strMod: Int,
    val dexMod: Int,
    val conMod: Int,
    val intMod: Int,
    val wisMod: Int,
    val chaMod: Int,

    val proficiencyBonus: Int,

    val maxHp: Int,
    val currentHp: Int,

    val armorClass: Int,

    val savingThrowProficiencies: List<String>,
    val skillProficiencies: List<String>,

    val speed: Int,
    val initiative: Int,

    val equipmentNotes: String
) : Serializable {
    companion object {
        val ALL_SAVING_THROWS = listOf(
            "STR",
            "DEX",
            "CON",
            "INT",
            "WIS",
            "CHA"
        )
        val ALL_SKILLS = listOf(
            "Acrobatics",
            "Animal Handling",
            "Arcana",
            "Athletics",
            "Deception",
            "History",
            "Insight",
            "Intimidation",
            "Investigation",
            "Medicine",
            "Nature",
            "Perception",
            "Performance",
            "Persuasion",
            "Religion",
            "Sleight of Hand",
            "Stealth",
            "Survival"
        )
    }
}
