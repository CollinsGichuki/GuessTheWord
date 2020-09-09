package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    // Encapsulating word, score and eventGameFinished variables
    // They should be changed only within the ViewModel(Mutable)
    // They should be immutable to other classes(Only read)

    private val _word = MutableLiveData<String>()

    //immutable value for word
    val word: LiveData<String>
        get() = _word

    // Private value for the current score
    private val _score = MutableLiveData<Int>()

    //score which is immutable can be accessed outside the ViewModel
    val score: LiveData<Int>
        get() = _score

    // Event which triggers the end of the game
    private val _eventGameFinnish = MutableLiveData<Boolean>()
    val eventGameFinnish: LiveData<Boolean>
        get() = _eventGameFinnish

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    init {
        //Called whenever the view model is recreated

        _word.value = ""
        _score.value = 0
        resetList()
        nextWord()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        if (wordList.isEmpty()) {
            //End the game
            onGameFinished()
        } else {
            //Select and remove a word from the list
            _word.value = wordList.removeAt(0)
        }
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    //Method for Game Finished event
    fun onGameFinished() {
        _eventGameFinnish.value = true
    }

    //Method to reset the gameFinished event
    fun onGameFinishComplete() {
        _eventGameFinnish.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel is destroyed!")
    }
}