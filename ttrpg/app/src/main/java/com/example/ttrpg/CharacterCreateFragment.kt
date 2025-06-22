package com.example.ttrpg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ttrpg.data.Character
import com.example.ttrpg.databinding.FragmentCharacterCreateBinding
import com.example.ttrpg.viewmodel.CharacterViewModel

class CharacterCreateFragment : Fragment() {

    private var _binding: FragmentCharacterCreateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveCharacter.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val race = binding.etRace.text.toString().trim()
            val level = binding.etLevel.text.toString().toIntOrNull() ?: 1
            val experience = binding.etExperience.text.toString().toIntOrNull() ?: 0

            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "Введите имя персонажа", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val str = binding.etStrength.text.toString().toIntOrNull() ?: 10
            val dex = binding.etDexterity.text.toString().toIntOrNull() ?: 10
            val con = binding.etConstitution.text.toString().toIntOrNull() ?: 10
            val intel = binding.etIntelligence.text.toString().toIntOrNull() ?: 10
            val wis = binding.etWisdom.text.toString().toIntOrNull() ?: 10
            val cha = binding.etCharisma.text.toString().toIntOrNull() ?: 10

            val strMod = binding.etStrMod.text.toString().toIntOrNull() ?: (str - 10) / 2
            val dexMod = binding.etDexMod.text.toString().toIntOrNull() ?: (dex - 10) / 2
            val conMod = binding.etConMod.text.toString().toIntOrNull() ?: (con - 10) / 2
            val intMod = binding.etIntMod.text.toString().toIntOrNull() ?: (intel - 10) / 2
            val wisMod = binding.etWisMod.text.toString().toIntOrNull() ?: (wis - 10) / 2
            val chaMod = binding.etChaMod.text.toString().toIntOrNull() ?: (cha - 10) / 2

            val proficiencyBonus = binding.etProficiencyBonus.text.toString().toIntOrNull() ?: 2
            val maxHp = binding.etMaxHp.text.toString().toIntOrNull() ?: 10
            val currentHp = binding.etCurrentHp.text.toString().toIntOrNull() ?: maxHp
            val armorClass = binding.etArmorClass.text.toString().toIntOrNull() ?: 10
            val speed = binding.etSpeed.text.toString().toIntOrNull() ?: 30
            val initiative = binding.etInitiative.text.toString().toIntOrNull() ?: dexMod

            val savingThrowProficiencies = mutableListOf<String>().apply {
                if (binding.cbStrSave.isChecked) add("STR")
                if (binding.cbDexSave.isChecked) add("DEX")
                if (binding.cbConSave.isChecked) add("CON")
                if (binding.cbIntSave.isChecked) add("INT")
                if (binding.cbWisSave.isChecked) add("WIS")
                if (binding.cbChaSave.isChecked) add("CHA")
            }

            val skillProficiencies = mutableListOf<String>().apply {
                if (binding.cbAcrobatics.isChecked) add("Acrobatics")
                if (binding.cbAnimalHandling.isChecked) add("Animal Handling")
                if (binding.cbArcana.isChecked) add("Arcana")
                if (binding.cbAthletics.isChecked) add("Athletics")
                if (binding.cbDeception.isChecked) add("Deception")
                if (binding.cbHistory.isChecked) add("History")
                if (binding.cbInsight.isChecked) add("Insight")
                if (binding.cbIntimidation.isChecked) add("Intimidation")
                if (binding.cbInvestigation.isChecked) add("Investigation")
                if (binding.cbMedicine.isChecked) add("Medicine")
                if (binding.cbNature.isChecked) add("Nature")
                if (binding.cbPerception.isChecked) add("Perception")
                if (binding.cbPerformance.isChecked) add("Performance")
                if (binding.cbPersuasion.isChecked) add("Persuasion")
                if (binding.cbReligion.isChecked) add("Religion")
                if (binding.cbSleightOfHand.isChecked) add("Sleight of Hand")
                if (binding.cbStealth.isChecked) add("Stealth")
                if (binding.cbSurvival.isChecked) add("Survival")
            }

            val equipmentNotes = binding.etEquipmentNotes.text.toString().trim()

            val character = Character(
                name = name,
                race = race,
                level = level,
                experience = experience,
                strength = str,
                dexterity = dex,
                constitution = con,
                intelligence = intel,
                wisdom = wis,
                charisma = cha,
                strMod = strMod,
                dexMod = dexMod,
                conMod = conMod,
                intMod = intMod,
                wisMod = wisMod,
                chaMod = chaMod,
                proficiencyBonus = proficiencyBonus,
                maxHp = maxHp,
                currentHp = currentHp,
                armorClass = armorClass,
                savingThrowProficiencies = savingThrowProficiencies,
                skillProficiencies = skillProficiencies,
                speed = speed,
                initiative = initiative,
                equipmentNotes = equipmentNotes
            )

            viewModel.addCharacter(character)
            Toast.makeText(requireContext(), "Персонаж \"$name\" создан", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
