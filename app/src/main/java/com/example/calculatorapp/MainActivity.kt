package com.example.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null
    var lastDot = false
    var lastNumeric = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tvInput = findViewById(R.id.textView)
    }

    fun onDigit(view: View) {
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        tvInput?.text = ""
        lastDot = true
        lastNumeric = false
    }

    fun onOperator(view: View) {
        tvInput?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvInput?.append(".")
            lastDot = true
            lastNumeric = false
        }
    }

    fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            return false
        } else {
            value.contains("+")
                    || value.contains("-")
                    || value.contains("*")
                    || value.contains("/")
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = tvInput?.text
            var prefix = ""

            try {
                if (tvValue != null) {

                    if (tvValue.startsWith("-")) {
                        prefix = "-"
                        tvValue = tvValue.substring(1)
                    }

                    if(tvValue.contains("-")){
                        var array = tvValue?.split("-")
                        var firstNum = array?.get(0)
                        var secondNum = array?.get(1)

                        if (prefix == "-") firstNum = prefix + firstNum

                        if (firstNum != null) {
                            if (secondNum != null) {
                                tvInput?.text = isIntegerNum((firstNum.toDouble() - secondNum.toDouble()))
                            }
                        }
                    }else if(tvValue.contains("+")){
                        var array = tvValue?.split("+")
                        var firstNum = array?.get(0)
                        var secondNum = array?.get(1)

                        if (prefix == "-") firstNum = prefix + firstNum

                        if (firstNum != null) {
                            if (secondNum != null) {
                                tvInput?.text = isIntegerNum(firstNum.toDouble() + secondNum.toDouble())
                            }
                        }
                    }else if(tvValue.contains("*")){
                        var array = tvValue?.split("*")
                        var firstNum = array?.get(0)
                        var secondNum = array?.get(1)

                        if (prefix == "-") firstNum = prefix + firstNum

                        if (firstNum != null) {
                            if (secondNum != null) {
                                tvInput?.text = isIntegerNum(firstNum.toDouble() * secondNum.toDouble())
                            }
                        }
                    }else if(tvValue.contains("/")){
                        var array = tvValue?.split("/")
                        var firstNum = array?.get(0)
                        var secondNum = array?.get(1)

                        if (prefix == "-") firstNum = prefix + firstNum

                        if (firstNum != null) {
                            if (secondNum != null) {
                                tvInput?.text = isIntegerNum(firstNum.toDouble() / secondNum.toDouble())
                            }
                        }
                    }
                }
            } catch (e: ArithmeticException) {
                println("$e")
            }
        }
    }

    private fun isIntegerNum(d: Double): String {
        if ((d * 10) % 10 == 0.0) {
            return d.toInt().toString()
        }
        return d.toString()
    }
}









