package de.krauthoff.currencyconverter

fun runCurrencyParser(input: String): HashMap<String, String> {
    val map = hashMapOf("EUR" to "1.0")

    val lines = input.split("\n")
    for (line in lines){
        if ("currency" in line){
            val parts = line.trim().split(" ")
            val currency = parts[1].substring(10, 13)
            val value = parts[2].substring(6, parts[2].length - 3)
            map[currency] = value
        }
    }
    return map
}
