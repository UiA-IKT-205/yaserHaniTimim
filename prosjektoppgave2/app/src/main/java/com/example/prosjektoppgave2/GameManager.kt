package com.example.prosjektoppgave2

import android.content.Intent
import android.util.Log
import com.example.prosjektoppgave2.Api.GameService
import com.example.prosjektoppgave2.Api.data.Game
import com.example.prosjektoppgave2.Api.data.GameState
import com.example.prosjektoppgave2.App.Companion.context


typealias GameCallback = (game: Game?) -> Unit

object GameManager {
    val TAG:String = "GameManager"
    val StartingGameState:GameState = listOf(mutableListOf('0','0','0'),mutableListOf('0','0','0'),mutableListOf('0','0','0'))

    fun createGame(player:String){

        GameService.createGame(player,StartingGameState) { game: Game?, err: Int? ->
            if(err != null){
                Log.d(TAG, "Error starting game. Error code : $err")
            } else {
                val intent = Intent(context, GameActivity::class.java)
                intent.putExtra("game", game)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent)
            }
        }
    }

    fun joinGame(player:String, gameId:String){

        GameService.joinGame(player, gameId) { game: Game?, err: Int? ->
            if(err != null){
                Log.d(TAG, "Error joining game. Error code : $err")
            } else {
                val intent = Intent(context, GameActivity::class.java)
                intent.putExtra("game", game)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent)
            }
        }
    }

    fun pollGame(gameId:String, callback:GameCallback){

        GameService.pollGame(gameId) { game: Game?, err: Int? ->
            if(err != null){
                Log.d(TAG, "Error refreshing game. Error code : $err")
            } else {
                callback(game)
            }
        }

    }

    fun updateGame(gameId: String, state: GameState){

        GameService.updateGame(gameId, state) { game: Game?, err: Int? ->
            if(err != null){
                Log.d(TAG, "Error updating game. Error code : $err")
            }
            else {

                Log.d(TAG,"state:${game?.state}")
            }
        }
    }

}


//