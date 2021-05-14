package com.example.prosjektoppgave2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import com.example.prosjektoppgave2.Api.data.Game
import com.example.prosjektoppgave2.Api.data.GameState
import com.example.prosjektoppgave2.databinding.ActivityGameBinding
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    var turn: Boolean = false
    var symbol_1:Char= '\u0000'
    var symbol_2:Char = '\u0000'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var game: Game? = intent.getParcelableExtra("game")


        val buttons = listOf(one,two, three, four, five, six, seven, eight, nine)
        for (b in buttons){
            game?.let { getState(it.state,b) }
        }
        turn=true

    ///////////////////////////////////////////////////////
        one.setOnClickListener {

            if (one.text == '0'.toString()) {
//                                    for (b in buttons) {
//                                        game?.let { getState(it.state, b) }
//                                    }

                changeState(game, one)
                //                                  turn = false
                for (b in buttons) {
                    game?.let { getState(it.state, b) }
                }

                one.isEnabled = false


                win()
            }
//                                for (b in buttons) {
//                                    game?.let { getState(it.state, b) }
//                                }


        }

        two.setOnClickListener {
            if (two.text=='0'.toString()) {
                for (b in buttons){

                    game?.let { getState(it.state, b) }

                }
                changeState(game, two)
                for (b in buttons){
                    game?.let { getState(it.state, b) }
                }

                two.isEnabled = false


                //                              two.text = 'x'.toString()

                win()
            }
        }
        for (b in buttons){
            game?.let { getState(it.state, b) }
        }


        three.setOnClickListener {
            if(three.text=='0'.toString()){
                for (b in buttons){

                    game?.let { getState(it.state, b) }


                }
                changeState(game,three)
                for (b in buttons){
                    game?.let { getState(it.state, b) }
                }


                //                               three.text= 'x'.toString()

                win()
            }

        }
        for (b in buttons){
            game?.let { getState(it.state, b) }
        }


        four.setOnClickListener {
            if (four.text=='0'.toString()) {
                for (b in buttons){

                    game?.let { getState(it.state, b) }


                }
                changeState(game, four)


//                                four.text = 'x'.toString()

                win()
            }
        }
        for (b in buttons){
            game?.let { getState(it.state, b) }
        }


        five.setOnClickListener {
            if(five.text=='0'.toString()) {
                for (b in buttons){

                    game?.let { getState(it.state, b) }


                }
                changeState(game, five)
                for (b in buttons){
                    game?.let { getState(it.state, b) }
                }


                //                               five.text = 'x'.toString()

                win()
            }
        }
        for (b in buttons){
            game?.let { getState(it.state, b) }
        }


        six.setOnClickListener {
            if(six.text=='0'.toString()) {
                for (b in buttons){

                    game?.let { getState(it.state, b) }


                }
                changeState(game, six)
                for (b in buttons){
                    game?.let { getState(it.state, b) }
                }


                //                               six.text = 'x'.toString()

                win()
            }
        }
        for (b in buttons){
            game?.let { getState(it.state, b) }
        }


        seven.setOnClickListener {
            if(seven.text=='0'.toString()) {
                for (b in buttons){

                    game?.let { getState(it.state, b) }


                }
                changeState(game, seven)
                for (b in buttons){
                    game?.let { getState(it.state, b) }
                }


//                                seven.text = 'x'.toString()

                win()
            }
        }
        for (b in buttons){
            game?.let { getState(it.state, b) }
        }


        eight.setOnClickListener {
            if (eight.text=='0'.toString()) {
                for (b in buttons){

                    game?.let { getState(it.state, b) }


                }
                changeState(game, eight)
                for (b in buttons){
                    game?.let { getState(it.state, b) }
                }

                //                               eight.text = 'x'.toString()

                win()
            }
        }
        for (b in buttons){
            game?.let { getState(it.state, b) }
        }


        nine.setOnClickListener {
            if (nine.text=='0'.toString()) {
                for (b in buttons){

                    game?.let { getState(it.state, b) }


                }
                changeState(game, nine)
                for (b in buttons){
                    game?.let { getState(it.state, b) }
                }

                //                             nine.text = 'x'.toString()

                win()
            }
        }

        ///////////////////////////////////////77

        game_Id.text= game!!.gameId
        first_player.text= game.players[0].toString()

        if (game.players.size==1){
            symbol_1 = 'X'
            symbol_2 = 'O'
        }else{
            symbol_1= 'O'
            symbol_2 = 'X'
        }




        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                GameManager.pollGame(game?.gameId.toString()) { newGame: Game? ->

                    if (game?.players != newGame?.players && newGame != null)
                        with(binding) {
                            second_player.setText(newGame.players[1] )
                            first_player.setText(newGame.players[0] )

                        }

                    if (game?.state != newGame?.state && newGame != null) {
                        game = newGame
                        for (b in buttons){
                            game?.let { getState(it.state, b) }
                        }
                        win()




//                        for (b in buttons){
//                            game?.let { getState(it.state, b) }
//                        }

                        turn=true

                    }

                }
                mainHandler.postDelayed(this, 1000)
            }
        })
        for (b in buttons){

            game?.let { getState(it.state, b) }

        }
    }







private fun changeState(game: Game?,btn:Button) {
        if (turn) {

                when(btn) {

                    one -> game!!.state[0][0] = symbol_1
                    two -> game!!.state[0][1] = symbol_1
                    three -> game!!.state[0][2] = symbol_1
                    four -> game!!.state[1][0] = symbol_1
                    five -> game!!.state[1][1] = symbol_1
                    six -> game!!.state[1][2] = symbol_1
                    seven -> game!!.state[2][0] = symbol_1
                    eight -> game!!.state[2][1] = symbol_1
                    nine -> game!!.state[2][2] = symbol_1
                }


            game?.state?.let { GameManager.updateGame(game.gameId, it) }
                turn = false

            }

        }

    private fun getState(state: GameState, btn:Button) {

        when(btn){
            one ->btn.text=  state[0][0].toString()
            two -> two.text= state[0][1].toString()
            three ->three.text=  state[0][2].toString()
            four -> four.text = state[1][0].toString()
            five -> five.text= state[1][1].toString()
            six ->six.text= state[1][2].toString()
            seven ->seven.text=  state[2][0].toString()
            eight -> eight.text= state[2][1].toString()
            nine ->nine.text=  state[2][2].toString()
        }

    }

    private fun win(){
        if (one.text== two.text&& two.text==three.text&& one.text==symbol_1.toString()){
            celibirating()
        }
        else if (four.text== five.text&& five.text==six.text&& four.text==symbol_1.toString()){
            celibirating()
        }
        else if (seven.text== eight.text&& eight.text==nine.text&& seven.text==symbol_1.toString()){
            celibirating()
        }

       else if (one.text== four.text&& four.text==seven.text&& one.text==symbol_1.toString()){
            celibirating()
        }
       else  if (two.text== five.text&& five.text==eight.text&& two.text==symbol_1.toString()){
            celibirating()
        }

        else if (three.text== six.text&& six.text==nine.text&& three.text==symbol_1.toString()){
            celibirating()
        }

        else if (one.text== five.text&& five.text==nine.text&& one.text==symbol_1.toString()){
            celibirating()
        }
        else if (three.text== five.text&& five.text==seven.text&& three.text==symbol_1.toString()){
            celibirating()
        }



    }

    private fun celibirating(){
        val intent= Intent(this, Champion::class.java)
        startActivity(intent)
    }



}



