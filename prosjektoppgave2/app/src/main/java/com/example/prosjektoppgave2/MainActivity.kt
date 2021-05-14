package com.example.prosjektoppgave2

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.prosjektoppgave2.Api.GameService
import com.example.prosjektoppgave2.Api.GameServiceCallback
import com.example.prosjektoppgave2.Api.data.GameState
import com.example.prosjektoppgave2.App.Companion.context
import com.example.prosjektoppgave2.GameManager.StartingGameState
import com.example.prosjektoppgave2.GameManager
import com.example.prosjektoppgave2.databinding.ActivityMainBinding
import com.example.prosjektoppgave2.dialog.GameDialogListener
import com.example.prosjektoppgave2.dialog.JoinGameDialog
import com.example.prosjektoppgave2.thgame.dialogs.CreateGameDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_create_game.*
import org.json.JSONObject
import java.util.Collections.list
import kotlin.math.log
import com.example.prosjektoppgave2.Api.data.Game as Game


class MainActivity : AppCompatActivity(), GameDialogListener {

    private var TAG1 = "GAME"
    val TAG:String = "MainActivity"

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val player = player_name.text.toString()


        btn_start.setOnClickListener {
            createNewGame()

        }

        btn_join.setOnClickListener {
           joinGame()
        }

    }

    private fun createNewGame(){
        val dlg = CreateGameDialog()

        dlg.show(supportFragmentManager,"CreateGameDialogFragment")

    }

    private fun joinGame(){
        val dlg = JoinGameDialog()
        dlg.show(supportFragmentManager,"CreateGameDialogFragment")
    }

    override fun onDialogCreateGame(player: String) {
        Log.d(TAG,player)
        GameManager.createGame(player)


    }

    override fun onDialogJoinGame(player: String, gameId: String) {
        GameManager.joinGame(player,gameId)
        Log.d(TAG, "$player $gameId")
    }



    }





//    //// create game function///////////7


//    ///join function////////////////
//
//    fun joinGame(playerId:String,gameId:String){
//
//        val requestQue: RequestQueue = Volley.newRequestQueue(context)
//
//        val url ="https://generic-game-service.herokuapp.com/Game"
//        //               "https://generic-game-service.herokuapp.com/Game"
//        val requestData = JSONObject()
//        requestData.put("playerId", playerId)
//        requestData.put("gameId", gameId)
//
//
//        val request = object : JsonObjectRequest(Request.Method.POST, url, requestData,
//
//                Response.Listener { response ->
//                    // Success game created.
//                    var gamId = response["gameId"]
//                    val players = response["players"]
//
//                    val state = response["state"]
//                    val  gId=  response["gameId"].toString()
////                    callback(game, null)
//                    val game= Gson().fromJson(response.toString(0),Game::class.java)
//
//
//                    Log.d(ContentValues.TAG,"the game id ${gId}")
//                    val intent= Intent(this,GameActivity::class.java)
//                    intent.putExtra("game",game)
//
//                    startActivity(intent)
//                },
//                Response.ErrorListener { error ->
//                    // Error creating new game.
////            callback(null, error.networkResponse.statusCode)
//                }) {
//            override fun getHeaders(): MutableMap<String, String> {
//                val headers = HashMap<String, String>()
//                headers["Content-Type"] = "application/json"
//                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
//                return headers
//            }
//
//
//        }
//
//        requestQue.add(request)
//    }


/////////////////////////////////////////////////////////////////////////////////////////7
// the game
