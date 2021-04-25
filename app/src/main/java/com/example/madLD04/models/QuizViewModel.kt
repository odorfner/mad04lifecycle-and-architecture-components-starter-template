package com.example.madLD04.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class QuizViewModel: ViewModel() {
    val questions = QuestionCatalogue().defaultQuestions
    var score = MutableLiveData<Int>()
    var index = MutableLiveData<Int>()
    var selectedAnswer = MutableLiveData<Int>()
    var eventGameFinish = MutableLiveData<Boolean>()

    init {
        score.value = 0
        index.value = 0
    }

    fun nextQuestion(){
        // Update score
        if (questions[index.value!!].answers[selectedAnswer.value!!].isCorrectAnswer) {
            score.value = (score.value)?.plus(1)
        }
        // Check if it´s the last Question
        if (index.value == questions.size - 1) {
            // If it´s the last Question, then set the eventGameFinish to true
            onGameFinish()
        } else {
            // If it´s not the last Question, then increment the index with one
            index.value = (index.value)?.plus(1)
        }
    }

    //if last Question
    fun onGameFinish() {
        eventGameFinish.value = true
    }

    // It´s called when gameFinished() is called in QuizFragment
    fun onGameFinishComplete() {
        eventGameFinish.value = false
    }

}

