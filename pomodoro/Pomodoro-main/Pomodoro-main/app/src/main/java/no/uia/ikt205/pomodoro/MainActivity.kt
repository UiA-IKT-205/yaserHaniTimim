package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import android.widget.Toast
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime
import kotlin.math.absoluteValue


class MainActivity : AppCompatActivity() {


    lateinit var timer: CountDownTimer
    lateinit var startButton: Button
    lateinit var coutdownDisplay: TextView
    lateinit var seekBar: SeekBar
    lateinit var seekBarPause: SeekBar
    lateinit var Repeatitions: EditText


    var timeToCountDownInMs = 0L
    val timeTicks = 1000L
    var Pause = 0L


    fun updateCountDownDisplay(timeInMs: Long) {
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }
    


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            seekBar = findViewById<SeekBar>(R.id.SeekBar)
            seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

                    timeToCountDownInMs = progress.absoluteValue.toLong()
                    var convertToMilliSecond = timeToCountDownInMs * 60000
                    timeToCountDownInMs = convertToMilliSecond
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {

                    Toast.makeText(this@MainActivity, "Progress is " + seekBar.progress + "minute", Toast.LENGTH_SHORT).show()

                }
            })

            seekBarPause = findViewById<SeekBar>(R.id.SeekBarPause)
            seekBarPause?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBarPause: SeekBar, progress: Int, fromUser: Boolean) {

                    Pause = progress.absoluteValue.toLong()
                    var conv: Long = Pause * 6000
                    Pause = conv
                }

                override fun onStartTrackingTouch(seekBarPause: SeekBar) {

                }

                override fun onStopTrackingTouch(seekBarPause: SeekBar) {
                    Toast.makeText(this@MainActivity, "the pause is " + seekBarPause.progress + "minute", Toast.LENGTH_SHORT).show()


                }
            })



            Repeatitions = findViewById<EditText>(R.id.readLine)
            Repeatitions.setText("").toString()
            var reps = Repeatitions.inputType.toInt()


            fun startCountDown(v: View) {


                timer = object : CountDownTimer(timeToCountDownInMs, timeTicks) {


                    override fun onFinish() {

                        Thread.sleep(Pause)

                        Toast.makeText(this@MainActivity, "Arbeidsøkt er i pause", Toast.LENGTH_SHORT).show()
                        if (reps == 0) {
                            timer.cancel()
                        } else {
                            reps = reps?.minus(1)
                            timer.start()
                        }
                    }


                    override fun onTick(millisUntilFinished: Long) {
                        updateCountDownDisplay(millisUntilFinished)
                    }
                }

                timer.start()
                coutdownDisplay = findViewById<TextView>(R.id.countDownView)
            }



            startButton = findViewById<Button>(R.id.startCountdownButton)
            startButton.setOnClickListener() {
                Toast.makeText(this@MainActivity, "Arbeidsøkt er i pause", Toast.LENGTH_SHORT).show()
                startCountDown(it)
            }
        }

    }





