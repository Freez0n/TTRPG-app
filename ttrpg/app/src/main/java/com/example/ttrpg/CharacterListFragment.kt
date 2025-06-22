package com.example.ttrpg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ttrpg.data.Character
import com.example.ttrpg.databinding.FragmentCharacterListBinding
import com.example.ttrpg.databinding.ItemCharacterBinding
import com.example.ttrpg.viewmodel.CharacterViewModel

class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerCharacters.layoutManager = LinearLayoutManager(requireContext())

        val adapter = CharacterAdapter(
            onClick = { character ->
                val action = CharacterListFragmentDirections
                    .actionCharacterListFragmentToCharacterDetailFragment(character.id.toLong())
                findNavController().navigate(action)
            },
            onLongClick = { character ->
                viewModel.deleteCharacter(character)
            }
        )

        binding.recyclerCharacters.adapter = adapter

        viewModel.allCharacters.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        binding.fabCreateCharacter.setOnClickListener {
            findNavController().navigate(R.id.action_characterListFragment_to_characterCreateFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private class CharacterAdapter(
        val onClick: (Character) -> Unit,
        val onLongClick: (Character) -> Unit
    ) : ListAdapter<Character, CharacterAdapter.VH>(DIFF) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val binding = ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return VH(binding, onClick, onLongClick)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.bind(getItem(position))
        }

        class VH(
            private val binding: ItemCharacterBinding,
            onClick: (Character) -> Unit,
            onLongClick: (Character) -> Unit
        ) : RecyclerView.ViewHolder(binding.root) {
            private var current: Character? = null

            init {
                binding.root.setOnClickListener { current?.let(onClick) }
                binding.btnDeleteCharacter.setOnClickListener {
                    current?.let(onLongClick)
                }
            }

            fun bind(character: Character) {
                current = character
                binding.tvCharacterName.text = character.name
                binding.tvCharacterDetails.text =
                    "Lv ${character.level} • ${character.race} • HP ${character.currentHp}/${character.maxHp}"
            }
        }

        companion object {
            private val DIFF = object : DiffUtil.ItemCallback<Character>() {
                override fun areItemsTheSame(a: Character, b: Character) = a.id == b.id
                override fun areContentsTheSame(a: Character, b: Character) = a == b
            }
        }
    }
}
