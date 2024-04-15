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
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    lateinit var tipPercentage : TextView
    lateinit var seekBar: SeekBar
    lateinit var billValueInput: String
    lateinit var tipValue : TextView
    lateinit var billValue : TextView
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
        seekBar = findViewById(R.id.seekBar)
        tipPercentage = findViewById(R.id.tipPercentageValue)
        tipPercentage.text = "${seekBar.progress}%"

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // here, you react to the value being set in seekBar
                billValueInput = findViewById<TextView?>(R.id.billValueInput).text.toString()
                tipValue = findViewById(R.id.tipValue)
                billValue = findViewById(R.id.billValue)

                val decimalFormat = DecimalFormat("##.##")

                if(billValueInput.isNotEmpty() && billValueInput != "") {
                    val calcTip = (seekBar.progress.toDouble()/100) * billValueInput.toDouble()
                    tipValue.text = decimalFormat.format(calcTip)

                    val billCalc = calcTip + billValueInput.toDouble()
                    billValue.text = decimalFormat.format(billCalc)
                }else{
                    Snackbar.make(
                        findViewById(R.id.main),
                        "Inform the bill value",
                        Snackbar.ANIMATION_MODE_SLIDE
                    ).show()
                }
                tipPercentage.text = "${seekBar.progress}%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar){}
            override fun onStopTrackingTouch(seekBar: SeekBar){}
        })
    }

    fun textWatcherSetup(){
        val textField : TextView = findViewById(R.id.billValueInput)
        textField.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                seekBar = findViewById(R.id.seekBar)
                billValueInput = findViewById<TextView?>(R.id.billValueInput).text.toString()
                tipValue = findViewById(R.id.tipValue)
                billValue = findViewById(R.id.billValue)

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