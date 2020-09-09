/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding

    private lateinit var gameViewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        //Initialize the viewModel
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        //Assign the ViewModel to the DataBinding
        // This allows the bound layout(game_fragment) to directly access the data in the ViewModel
        binding.gameViewModel = gameViewModel

        //Specifying the fragment view as the lifecycle owner of the binding
        //This is used so that the binding can observe the liveData updates
        binding.lifecycleOwner = viewLifecycleOwner

        //Attaching Observer to the gameFinish event
        gameViewModel.eventGameFinnish.observe(viewLifecycleOwner, Observer<Boolean> { hasFinished -> if (hasFinished) endGame() })

        binding.endGameButton.setOnClickListener { endGame() }

        return binding.root

    }

    private fun endGame() {
        //Get the score and navigate to the score fragment
        Toast.makeText(activity, "Game is finished", Toast.LENGTH_SHORT).show()
        val action = GameFragmentDirections.actionGameToScore()
        action.score = gameViewModel.score.value ?: 0
        NavHostFragment.findNavController(this).navigate(action)

        //Reset the game finished event
        gameViewModel.onGameFinishComplete()
    }
}
