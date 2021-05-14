package com.example.prosjektoppgave2.Api

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.prosjektoppgave2.GameActivity
import com.example.prosjektoppgave2.*
import com.example.prosjektoppgave2.Api.data.Game
import com.example.prosjektoppgave2.Api.data.GameState
import com.google.gson.Gson
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONObject

typealias GameServiceCallback = (state: Game?, errorCode:Int? ) -> Unit

object GameService {
    private val context = App.context
    val TAG:String = "GameService"
    private val requestQue:RequestQueue = Volley.newRequestQueue(context)

    private enum class APIEndpoints(val url:String) {
        CREATE_GAME("%1s%2s%3s".format(context.getString(R.string.protocol), context.getString(R.string.domain),context.getString(R.string.base_path)))
    }

    fun createGame(playerId:String, state:GameState, callback:GameServiceCallback) {
        val url = APIEndpoints.CREATE_GAME.url
        val requestData = JSONObject()
        requestData.put("player", playerId)
        requestData.put("state",state)

        val request = object : JsonObjectRequest(Request.Method.POST,url, requestData,
                {
                    val players = Json.decodeFromString<MutableList<String>>(it.get("players").toString())
                    val gameId:String = it.get("gameId").toString()
                    val state = Json.decodeFromString<List<MutableList<Char>>>(it.get("state").toString())

                    val game = Game(players, gameId, state)
                    Log.d(TAG, "Starting : $game")
                    callback(game,null)
                }, {
            // Error creating new game.
            callback(null, it.networkResponse.statusCode)
        } ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
                return headers
            }
        }
        requestQue.add(request)
    }

    fun joinGame(playerId:String, gameId:String, callback: GameServiceCallback){
        val url = APIEndpoints.CREATE_GAME.url+"/"+gameId+"/join"
        val requestData = JSONObject()
        requestData.put("player", playerId)
        requestData.put("gameId",gameId)

        val request = object : JsonObjectRequest(Request.Method.POST,url, requestData,
                {
                    val players = Json.decodeFromString<MutableList<String>>(it.get("players").toString())
                    val gameId:String = it.get("gameId").toString()
                    val state = Json.decodeFromString<List<MutableList<Char>>>(it.get("state").toString())

                    val game = Game(players, gameId, state)
                    Log.d(TAG, "Joining : $game")
                    callback(game,null)
                }, {
            // Error creating new game.
            callback(null, it.networkResponse.statusCode)
        } ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
                return headers
            }
        }
        requestQue.add(request)

    }

    fun updateGame(gameId: String, gameState:GameState, callback: GameServiceCallback){

        val url = APIEndpoints.CREATE_GAME.url+"/"+gameId+"/update"
        val requestData = JSONObject()
        requestData.put("gameId", gameId)
        requestData.put("state", gameState)

        val request = object : JsonObjectRequest(Request.Method.POST,url, requestData,
                {
                    val players = Json.decodeFromString<MutableList<String>>(it.get("players").toString())
                    val gameId:String = it.get("gameId").toString()
                    val state = Json.decodeFromString<List<MutableList<Char>>>(gameState.toString())

                    val game = Game(players, gameId, state)
                    Log.d(TAG,"state:$state")
                    Log.d(TAG,"Updating : $game")
                    callback(game,null)
                }, {
            // Error creating new game.
            callback(null, it.networkResponse.statusCode)
        } ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
                return headers
            }
        }
        requestQue.add(request)

    }

    fun pollGame(gameId: String,callback:GameServiceCallback){
        val url = APIEndpoints.CREATE_GAME.url+"/"+gameId+"/poll"
        val requestData = JSONObject()
        val request = object : JsonObjectRequest(Request.Method.GET,url, requestData,
                {
                    val players = Json.decodeFromString<MutableList<String>>(it.get("players").toString())
                    val state = Json.decodeFromString<List<MutableList<Char>>>(it.get("state").toString())

                    val game = Game(players, gameId, state)
                    Log.d(TAG,"Polling : $game")
                    callback(game,null)
                }, {
            // Error creating new game.
            callback(null, it.networkResponse.statusCode)
        } ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
                return headers
            }
        }
        requestQue.add(request)
    }

}




//
//
//typealias GameServiceCallback = (state:Game?, errorCode:Int? ) -> Unit
//
///*  NOTE:
//    Using object expression to make GameService a Singleton.
//    Why? Because there should only be one active GameService ever.
// */
//val TAG_CREATE=  "create response"
//val TAG_UPDATE= "update response"
//val TAG_JOIN = "join response"
//val TAG_POLL= "poll response"
//
//object GameService {
//
//    /// NOTE: Do not want to have App.context all over the code. Also it is nice if we later want to support different contexts
//    val context = App.context
//
//    /// NOTE: God practice to use a que for performing requests.
//    private val requestQue: RequestQueue = Volley.newRequestQueue(context)
//
//    /// NOTE: One posible way of constructing a list of API url. You want to construct the urls so that you can support different environments (i.e. Debug, Test, Prod etc)
//    private enum class APIEndpoints(val url: String) {
//        CREATE_GAME(
//            "%1s%2s%3s".format(
//                context.getString(R.string.protocol),
//                context.getString(R.string.domain),
//                context.getString(R.string.base_path)
//            )
//        )
//
//    }
//
//
//
//    fun createGame(playerId: String, state: GameState, callback: GameServiceCallback) {
//
//        val url = APIEndpoints.CREATE_GAME.url
//
//        val requestData = JSONObject()
//        requestData.put("player", playerId)
//        requestData.put("state", JSONArray(state))
//
//        val request = object : JsonObjectRequest(Request.Method.POST, url, requestData,
//            {
//                // Success game created.
//                    var game = Gson().fromJson(it.toString(0), Game::class.java)
//                val gId = it.getString("gameId")
//                val player = it.getString("players").toString()
//                val state = it.getString("state").toString()
//                    callback(game,null)
//                Log.d(TAG_CREATE, "gid: ${game.gameId}")
//                val intent = Intent(context, GameActivity::class.java)
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("game",game)
//                intent.putExtra("gId",gId)
//                context.startActivity(intent)
//                Log.d(TAG_CREATE, "player: ${player}")
//                Log.d(TAG_CREATE, "state: ${state}")
//
//            }, {
//                // Error creating new game.
//                callback(null, it.networkResponse.statusCode)
//            }) {
//            override fun getHeaders(): MutableMap<String, String> {
//                val headers = HashMap<String, String>()
//                headers["Content-Type"] = "application/json"
//                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
//                return headers
//            }
//        }
//
//        requestQue.add(request)
//    }
//
//
//    fun joinGame(playerId: String, gameId: String, callback: GameServiceCallback) {
//
//        val url = "https://generic-game-service.herokuapp.com/Game/${gameId}/join"
//        val requestData = JSONObject()
//        requestData.put("playerId", playerId)
//        requestData.put("gameId", gameId)
//
//        val request = object : JsonObjectRequest(Request.Method.POST, url, requestData,
//                {
//                    // Success game created.
////
//                    val gId = it.getString("gameId").toString()
//                    val state = it.getString("state").toString()
//                    val players = it.getString("players").toString()
//                    var game = Gson().fromJson(it.toString(0), Game::class.java)
////                    callback(game,null)
//                    Log.d(TAG_JOIN, "gid: ${gId}")
//                    Log.d(TAG_JOIN, "state: ${state}")
//                    Log.d(TAG_JOIN, "players: ${players}")
//                    val intent= Intent(context,GameActivity::class.java)
//                    intent.putExtra("game",game)
//                    context.startActivity(intent)
//
//                }, {
//            // Error creating new game.
//            callback(null, it.networkResponse.statusCode)
//        }) {
//            override fun getHeaders(): MutableMap<String, String> {
//                val headers = HashMap<String, String>()
//                headers["Content-Type"] = "application/json"
//                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
//                return headers
//            }
//        }
//
//        requestQue.add(request)
//    }
//
//    fun updateGame(gameId: String, gameState: GameState, callback: GameServiceCallback) {
//        val url = APIEndpoints.CREATE_GAME.url+"/"+gameId+"/update"
//        val requestData = JSONObject()
//        requestData.put("gameId", gameId)
//        requestData.put("state", gameState)
//
//        val request = object : JsonObjectRequest(Request.Method.POST,url, requestData,
//                {
////                    val players = Json.decodeFromString<MutableList<String>>(it.get("players").toString())
////                    val gameId:String = it.get("gameId").toString()
////                    val state = Json.decodeFromString<List<MutableList<Char>>>(gameState.toString())
////
////                    val game = Game(players, gameId, state)
////                    Log.d(TAG,"Updating : $game")
////                    callback(game,null)
//                }, {
//            // Error creating new game.
//            callback(null, it.networkResponse.statusCode)
//        } ) {
//            override fun getHeaders(): MutableMap<String, String> {
//                val headers = HashMap<String, String>()
//                headers["Content-Type"] = "application/json"
//                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
//                return headers
//            }
//        }
//        requestQue.add(request)
//
//
//
//    }
//
//
//    fun pollGame(gameId: String, callback: GameServiceCallback) {
//        val url = "https://generic-game-service.herokuapp.com/Game/${gameId}/poll"
//
//        val requestData = JSONObject()
//        requestData.put("gameId", gameId)
//
//
//        val request = object : JsonObjectRequest(Request.Method.GET, url, requestData,
//            {
//                // Success game created.
////
////                val gId = it.getString("gameId").toString()
//                val statePoll = it.get("state")
////                val players = it.getString("players").toString()
////                var game = Gson().fromJson(it.toString(0), Game::class.java)
////                val players = Json.decodeFromString<MutableList<String>>(it.get("players").toString())
////                val statePoll = Json.decodeFromString<List<MutableList<Char>>>(it.get("state").toString())
//
//
////                    callback(game,null)
////                Log.d(TAG_POLL, "gid: ${gId}")
//                Log.d(TAG_POLL, "state: ${statePoll}")
////                Log.d(TAG_POLL, "players: ${players}")
//
//
//
//
//            }, {
//                // Error creating new game.
//                callback(null, it.networkResponse.statusCode)
//            }) {
//            override fun getHeaders(): MutableMap<String, String> {
//                val headers = HashMap<String, String>()
//                headers["Content-Type"] = "application/json"
//                headers["Game-Service-Key"] =
//                    GameService.context.getString(R.string.game_service_key)
//                return headers
//            }
//
//        }
//        requestQue.add(request)
//
//    }
//}
//
//
//
