package com.example.ttrpg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ttrpg.data.Character
import com.example.ttrpg.databinding.FragmentCharacterDetailBinding
import com.example.ttrpg.viewmodel.CharacterViewModel

class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterViewModel by viewModels()
    private val args: CharacterDetailFragmentArgs by navArgs()

    private lateinit var original: Character

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterId = args.characterId

        viewModel.getCharacterById(characterId).observe(viewLifecycleOwner) { character ->
            if (character == null) {
                Toast.makeText(requireContext(), "Персонаж не найден", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
                return@observe
            }

            original = character
            bindCharacterData()
        }

        binding.btnSaveCharacter.setOnClickListener {
            if (!::original.isInitialized) return@setOnClickListener

            val updated = original.copy(
                name       = binding.etName.text.toString().trim(),
                race       = binding.etRace.text.toString().trim(),
                level      = binding.etLevel.text.toString().toIntOrNull() ?: original.level,
                experience = binding.etExperience.text.toString().toIntOrNull() ?: original.experience,

                strength   = binding.etStrength.text.toString().toIntOrNull() ?: original.strength,
                strMod     = binding.etStrMod.text.toString().toIntOrNull() ?: original.strMod,
                dexterity  = binding.etDexterity.text.toString().toIntOrNull() ?: original.dexterity,
                dexMod     = binding.etDexMod.text.toString().toIntOrNull() ?: original.dexMod,
                constitution = binding.etConstitution.text.toString().toIntOrNull() ?: original.constitution,
                conMod       = binding.etConMod.text.toString().toIntOrNull() ?: original.conMod,
                intelligence = binding.etIntelligence.text.toString().toIntOrNull() ?: original.intelligence,
                intMod       = binding.etIntMod.text.toString().toIntOrNull() ?: original.intMod,
                wisdom       = binding.etWisdom.text.toString().toIntOrNull() ?: original.wisdom,
                wisMod       = binding.etWisMod.text.toString().toIntOrNull() ?: original.wisMod,
                charisma     = binding.etCharisma.text.toString().toIntOrNull() ?: original.charisma,
                chaMod       = binding.etChaMod.text.toString().toIntOrNull() ?: original.chaMod,

                proficiencyBonus = binding.etProficiencyBonus.text.toString().toIntOrNull()
                    ?: original.proficiencyBonus,
                maxHp            = binding.etMaxHp.text.toString().toIntOrNull() ?: original.maxHp,
                currentHp        = binding.etCurrentHp.text.toString().toIntOrNull() ?: original.currentHp,
                armorClass       = binding.etArmorClass.text.toString().toIntOrNull() ?: original.armorClass,
                speed            = binding.etSpeed.text.toString().toIntOrNull() ?: original.speed,
                initiative       = binding.etInitiative.text.toString().toIntOrNull() ?: original.initiative,

                savingThrowProficiencies = listOfNotNull(
                    "STR".takeIf { binding.cbStrSave.isChecked },
                    "DEX".takeIf { binding.cbDexSave.isChecked },
                    "CON".takeIf { binding.cbConSave.isChecked },
                    "INT".takeIf { binding.cbIntSave.isChecked },
                    "WIS".takeIf { binding.cbWisSave.isChecked },
                    "CHA".takeIf { binding.cbChaSave.isChecked }
                ),

                skillProficiencies = listOfNotNull(
                    "Acrobatics".takeIf { binding.cbAcrobatics.isChecked },
                    "Animal Handling".takeIf { binding.cbAnimalHandling.isChecked },
                    "Arcana".takeIf { binding.cbArcana.isChecked },
                    "Athletics".takeIf { binding.cbAthletics.isChecked },
                    "Deception".takeIf { binding.cbDeception.isChecked },
                    "History".takeIf { binding.cbHistory.isChecked },
                    "Insight".takeIf { binding.cbInsight.isChecked },
                    "Intimidation".takeIf { binding.cbIntimidation.isChecked },
                    "Investigation".takeIf { binding.cbInvestigation.isChecked },
                    "Medicine".takeIf { binding.cbMedicine.isChecked },
                    "Nature".takeIf { binding.cbNature.isChecked },
                    "Perception".takeIf { binding.cbPerception.isChecked },
                    "Performance".takeIf { binding.cbPerformance.isChecked },
                    "Persuasion".takeIf { binding.cbPersuasion.isChecked },
                    "Religion".takeIf { binding.cbReligion.isChecked },
                    "Sleight of Hand".takeIf { binding.cbSleightOfHand.isChecked },
                    "Stealth".takeIf { binding.cbStealth.isChecked },
                    "Survival".takeIf { binding.cbSurvival.isChecked }
                ),

                equipmentNotes = binding.etEquipmentNotes.text.toString().trim()
            )

            viewModel.updateCharacter(updated)


            Toast.makeText(requireContext(), "Персонаж сохранён", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    private fun bindCharacterData() {
        with(binding) {
            etName.setText(original.name)
            etRace.setText(original.race)
            etLevel.setText(original.level.toString())
            etExperience.setText(original.experience.toString())

            etStrength.setText(original.strength.toString())
            etStrMod.setText(original.strMod.toString())
            etDexterity.setText(original.dexterity.toString())
            etDexMod.setText(original.dexMod.toString())
            etConstitution.setText(original.constitution.toString())
            etConMod.setText(original.conMod.toString())
            etIntelligence.setText(original.intelligence.toString())
            etIntMod.setText(original.intMod.toString())
            etWisdom.setText(original.wisdom.toString())
            etWisMod.setText(original.wisMod.toString())
            etCharisma.setText(original.charisma.toString())
            etChaMod.setText(original.chaMod.toString())

            etProficiencyBonus.setText(original.proficiencyBonus.toString())
            etMaxHp.setText(original.maxHp.toString())
            etCurrentHp.setText(original.currentHp.toString())
            etArmorClass.setText(original.armorClass.toString())
            etSpeed.setText(original.speed.toString())
            etInitiative.setText(original.initiative.toString())

            cbStrSave.isChecked = "STR" in original.savingThrowProficiencies
            cbDexSave.isChecked = "DEX" in original.savingThrowProficiencies
            cbConSave.isChecked = "CON" in original.savingThrowProficiencies
            cbIntSave.isChecked = "INT" in original.savingThrowProficiencies
            cbWisSave.isChecked = "WIS" in original.savingThrowProficiencies
            cbChaSave.isChecked = "CHA" in original.savingThrowProficiencies

            cbAcrobatics.isChecked = "Acrobatics" in original.skillProficiencies
            cbAnimalHandling.isChecked = "Animal Handling" in original.skillProficiencies
            cbArcana.isChecked = "Arcana" in original.skillProficiencies
            cbAthletics.isChecked = "Athletics" in original.skillProficiencies
            cbDeception.isChecked = "Deception" in original.skillProficiencies
            cbHistory.isChecked = "History" in original.skillProficiencies
            cbInsight.isChecked = "Insight" in original.skillProficiencies
            cbIntimidation.isChecked = "Intimidation" in original.skillProficiencies
            cbInvestigation.isChecked = "Investigation" in original.skillProficiencies
            cbMedicine.isChecked = "Medicine" in original.skillProficiencies
            cbNature.isChecked = "Nature" in original.skillProficiencies
            cbPerception.isChecked = "Perception" in original.skillProficiencies
            cbPerformance.isChecked = "Performance" in original.skillProficiencies
            cbPersuasion.isChecked = "Persuasion" in original.skillProficiencies
            cbReligion.isChecked = "Religion" in original.skillProficiencies
            cbSleightOfHand.isChecked = "Sleight of Hand" in original.skillProficiencies
            cbStealth.isChecked = "Stealth" in original.skillProficiencies
            cbSurvival.isChecked = "Survival" in original.skillProficiencies

            etEquipmentNotes.setText(original.equipmentNotes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
