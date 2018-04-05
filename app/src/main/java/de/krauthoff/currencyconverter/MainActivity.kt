package de.krauthoff.currencyconverter

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.DecimalFormat

class MainActivity : AppCompatActivity(), Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        textViewMXNValue.keyListener = null
        textViewUSDValue.keyListener = null

        redo.setOnClickListener { view ->
            Snackbar.make(view, "Data will be reloaded", Snackbar.LENGTH_LONG).show()
            displayResult(hashMapOf("MXN" to "--", "USD" to ""))
            doSomething()
        }

        doSomething()
    }

    override fun progressUI(visibility: Int) {
        progressBar.visibility = visibility
    }

    override fun displayResult(resultMap: HashMap<String, String>) {
        var map = resultMap
        if (resultMap.keys.count() < 3) {
            map = getFromSettings()
        } else {
            saveToSettings(resultMap)
        }
        textViewMXNValue.text = map.get("MXN")
        textViewUSDValue.text = map.get("USD")
    }

    private fun saveToSettings(resultMap: HashMap<String, String>) {
        val settings = getSharedPreferences("Currencys", 0)
        val editor = settings.edit()
        editor.putString("MXN", resultMap.get("MXN"))
        editor.putString("USD", resultMap.get("USD"))
        editor.apply()
    }

    private fun getFromSettings(): HashMap<String, String> {
        val map = hashMapOf("EUR" to "1.0")
        val prefs = this.getSharedPreferences("Currencys", 0)
        map["MXN"] = prefs.getString("MXN", "0.0")
        map["USD"] = prefs.getString("USD", "0.0")
        return map
    }

    fun execRadioListener(v: View) {
        var input = 0.0
        var output = 0.0

        when (radioButtonGroupInput.checkedRadioButtonId) {
            radioButtonEurInput.id -> input = 1.0
            radioButtonMxnInput.id -> input = textViewMXNValue.text.toString().toDouble()
            radioButtonUsdInput.id -> input = textViewUSDValue.text.toString().toDouble()
        }
        when (radioButtonGroupOutput.checkedRadioButtonId) {
            radioButtonEurOutput.id -> output = 1.0
            radioButtonMxnOutput.id -> output = textViewMXNValue.text.toString().toDouble()
            radioButtonUsdOutput.id -> output = textViewUSDValue.text.toString().toDouble()
        }

        var value = editTextInput.text.toString()
        if (value.isEmpty()) {
            value = "0.0"
        }

        val result = value.toDouble() / input * output

        val dec = DecimalFormat("#0.000")
        textViewOutput.text = dec.format(result)

    }

    private fun doSomething() {
        progressUI(View.INVISIBLE)
        GetCurrencys(this).execute()
    }
}