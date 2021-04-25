package com.example.madLD04

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.madLD04.databinding.FragmentQuizBinding
import com.example.madLD04.models.QuestionCatalogue


class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private val questions = QuestionCatalogue().defaultQuestions    // get a list of questions for the game
    private var score = 0                                           // save the user's score
    private var index = 0                                           // index for question data to show

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)

        binding.index = index
        binding.questionsCount = questions.size
        binding.question = questions[index]

        binding.btnNext.setOnClickListener {
            nextQuestion()
        }

        return binding.root
    }

    private fun nextQuestion(){
        val checkedId: Int = binding.answerBox.checkedRadioButtonId
        var answerIndex = 0

        when (checkedId) {
            R.id.answer1 -> answerIndex = 0
            R.id.answer2 -> answerIndex = 1
            R.id.answer3 -> answerIndex = 2
            R.id.answer4 -> answerIndex = 3
        }

        if (questions[index].answers[answerIndex].isCorrectAnswer) {
            score++
            Log.i("score", score.toString())
        }

        index++

        if (index < questions.size) {
            binding.index = index
            binding.question = questions[index]
            binding.invalidateAll()
            binding.answerBox.clearCheck()
        } else {
            findNavController().navigate(
                QuizFragmentDirections.actionQuizFragmentToQuizEndFragment(score, questions.size)
            )
        }
    }
}