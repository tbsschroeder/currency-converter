package de.krauthoff.currencyconverter

import android.os.AsyncTask
import android.view.View
import java.io.IOException
import java.net.URL

class GetCurrencys constructor(private val listener: Listener): AsyncTask<Any, Void, String>() {

    private val url = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml"

    override fun doInBackground(vararg params: Any?): String? {
        return try {
            URL(url).openStream().bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            return null
        }
    }

    override fun onPreExecute() {
        super.onPreExecute()
        listener.progressUI(View.VISIBLE)
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        listener.progressUI(View.INVISIBLE)
        listener.displayResult(runCurrencyParser(result ?: ""))
    }
}
