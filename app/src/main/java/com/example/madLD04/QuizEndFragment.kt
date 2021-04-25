package com.example.madLD04

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.madLD04.databinding.FragmentQuizEndBinding


class QuizEndFragment : Fragment() {
    private lateinit var binding: FragmentQuizEndBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_end, container, false)

        // get score from navigation arguments
        val args = QuizEndFragmentArgs.fromBundle(requireArguments())
        // show score
        binding.textView7.text = "${args.score}/${args.size}"
        binding.restartBtn.setOnClickListener{view: View ->
            view.findNavController().navigate(QuizEndFragmentDirections.actionQuizEndFragmentToQuizFragment())
        }

        return binding.root
    }
}