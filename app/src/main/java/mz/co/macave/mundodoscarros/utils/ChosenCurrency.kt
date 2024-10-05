package mz.co.macave.mundodoscarros.utils

object ChosenCurrency {

    var currency: String = "BRL"
    var rate: Double = 1.0

    fun changeCurrency(currency: String, rate: Double) {
        this.currency = currency
        this.rate = rate
    }
}