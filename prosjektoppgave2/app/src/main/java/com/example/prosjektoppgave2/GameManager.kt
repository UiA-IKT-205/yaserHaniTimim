package com.example.prosjektoppgave2

import com.example.prosjektoppgave2.Api.GameService
import com.example.prosjektoppgave2.Api.data.Game
import com.example.prosjektoppgave2.Api.data.GameState


object GameManager {

    var player:String? = null
    var state:GameState? = null

    val StartingGameState:GameState = listOf(listOf(0,0,0),listOf(0,0,0),listOf(0,0,0))

    fun createGame(player:String){

        GameService.createGame(player,StartingGameState) { game: Game?, err: Int? ->
            if(err != null){
                ///TODO("What is the error code? 406 you forgot something in the header. 500 the server di not like what you gave it")
            } else {
                /// TODO("We have a game. What to do?)
            }
        }

    }



}