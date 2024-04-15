package com.example.tipcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        listenerSetup()
    }

    fun listenerSetup(){

        seekBarSetup()
        textWatcherSetup()
    }

    fun seekBarSetup(){
        val seekBar : SeekBar = findViewById(R.id.seekBar)
        val tipPercentage : TextView = findViewById(R.id.tipPercentageValue)
        tipPercentage.text = "${seekBar.progress}%"

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // here, you react to the value being set in seekBar
                val billValueInput : String = findViewById<TextView?>(R.id.billValueInput).text.toString()
                val tipValue : TextView = findViewById(R.id.tipValue)
                val billValue : TextView = findViewById(R.id.billValue)

                val decimalFormat = DecimalFormat("##.##")

                if(billValueInput.isNotEmpty() && billValueInput != "") {
                    val calcTip = seekBar.progress.toDouble() * 0.01 * billValueInput.toDouble()
                    tipValue.text = decimalFormat.format(calcTip)

                    val billCalc = calcTip + billValueInput.toDouble()
                    billValue.text = decimalFormat.format(billCalc)
                }
                tipPercentage.text = "${seekBar.progress}%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar){}
            override fun onStopTrackingTouch(seekBar: SeekBar){}
        })
    }

    fun textWatcherSetup(){
        val billValueInput : TextView = findViewById(R.id.billValueInput)

        billValueInput.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val seekBar : SeekBar = findViewById(R.id.seekBar)
                val billValueInput : String = findViewById<TextView?>(R.id.billValueInput).text.toString()
                val tipValue : TextView = findViewById(R.id.tipValue)
                val billValue : TextView = findViewById(R.id.billValue)

                val decimalFormat = DecimalFormat("##.##")

                if(billValueInput.isNotEmpty() && billValueInput != "") {
                    val calcTip = seekBar.progress.toDouble() * 0.01 * billValueInput.toDouble()
                    tipValue.text = decimalFormat.format(calcTip)

                    val billCalc = calcTip + billValueInput.toDouble()
                    billValue.text = decimalFormat.format(billCalc)
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })


    }
}