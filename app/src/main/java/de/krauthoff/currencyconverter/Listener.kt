package de.krauthoff.currencyconverter

interface Listener {
    fun progressUI(visibility: Int)
    fun displayResult(resultMap: HashMap<String, String>)
}