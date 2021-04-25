package com.example.madLD04

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.madLD04.databinding.FragmentQuizBinding
import com.example.madLD04.models.QuizViewModel


class QuizFragment : Fragment() {
    private lateinit var binding: FragmentQuizBinding
    private lateinit var viewModel: QuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)
        // Initialize the ViewModel
        viewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
        Log.i("QuizFragment", "Called ViewModelProvider.get")
        // Add an observer to index from ViewModel
        viewModel.index.observe(viewLifecycleOwner, Observer { newIndex ->
            binding.index = newIndex
            binding.question = viewModel.questions[newIndex]
        })
        // Set the questionsCount size
        binding.questionsCount = viewModel.questions.size

        binding.btnNext.setOnClickListener {
            nextQuestion()
        }
        // Add an observer to the eventGameFinish
        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished) {
                gameFinished()
            }
        })
        return binding.root
    }

    //
    private fun nextQuestion(){
        val checkedId = binding.answerBox.checkedRadioButtonId
        // If nothing is selected
        if (checkedId == -1) {
            Toast.makeText(requireContext(),  "Please choose an answer!", Toast.LENGTH_SHORT).show()
            // Else find out the Index of the selected Answer
        } else {
            if (checkedId == R.id.answer1) {
                viewModel.selectedAnswer.value = 0
            } else if (checkedId == R.id.answer2) {
                viewModel.selectedAnswer.value = 1
            } else if (checkedId == R.id.answer3) {
                viewModel.selectedAnswer.value = 2
            } else if (checkedId == R.id.answer4) {
                viewModel.selectedAnswer.value = 3
            }
            // Call the nextQuestion()
            viewModel.nextQuestion()
            // Clear the Radio Button
            binding.answerBox.clearCheck()
        }
    }

    // If eventGameFinish is true, then this method is called
    fun gameFinished() {
        // Navigate to the QuizEndFragment
        val action = QuizFragmentDirections.actionQuizFragmentToQuizEndFragment (viewModel.score.value!!, viewModel.questions.size)
        NavHostFragment.findNavController(this).navigate(action)
        // Set the eventGameFinish to false again
        viewModel.onGameFinishComplete()
    }
}
