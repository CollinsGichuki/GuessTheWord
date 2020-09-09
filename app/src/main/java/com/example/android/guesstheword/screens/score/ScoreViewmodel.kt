package com.example.android.guesstheword.screens.score

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
// ViewModel with a Parameterized constructor
class ScoreViewModel(finalScore: Int): ViewModel() {
    // Encapsulating the final score variable
    // _score can only be accessed and hence changes in the ViewModel
    // score however can be accessed outside the ViewModel but it can't be changed since it is immutable
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
    get() = _score

    // Variable for the play again event handler
    private val _playAgain = MutableLiveData<Boolean>()
    val playAgain: LiveData<Boolean>
    get() = _playAgain

    init {
        _score.value = finalScore
    }

    fun onPlayAgain() {
        _playAgain.value = true
    }

    fun onPlayAgainComplete() {
        _playAgain.value = false
    }

}