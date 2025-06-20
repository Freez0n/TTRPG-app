package com.example.ttrpg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import kotlin.random.Random

class DiceFragment : Fragment() {

    private lateinit var resultTextView: TextView
    private lateinit var diceCountEditText: EditText
    private lateinit var resultScrollView: ScrollView
    private lateinit var totalTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_dice, container, false)

        resultTextView = root.findViewById(R.id.result_text_view)
        diceCountEditText = root.findViewById(R.id.dice_count_edit_text)
        resultScrollView = root.findViewById(R.id.result_scroll_view)
        totalTextView = root.findViewById(R.id.total_text_view)

        setupDiceButtons(root)

        return root
    }

    private fun setupDiceButtons(view: View) {
        val diceTypes = listOf(4, 6, 8, 10, 12, 20)
        for (dice in diceTypes) {
            val buttonId = resources.getIdentifier("button_d$dice", "id", requireContext().packageName)
            val button = view.findViewById<Button>(buttonId)
            button?.setOnClickListener {
                val count = diceCountEditText.text.toString().toIntOrNull() ?: 1
                rollDice(dice, count)
            }
        }
    }

    private fun rollDice(sides: Int, count: Int) {
        val results = List(count) { Random.nextInt(1, sides + 1) }
        val display = results.joinToString(", ")
        resultTextView.text = display
        totalTextView.text = "Сумма: ${results.sum()}"
        resultScrollView.post { resultScrollView.fullScroll(View.FOCUS_DOWN) }
    }
}
