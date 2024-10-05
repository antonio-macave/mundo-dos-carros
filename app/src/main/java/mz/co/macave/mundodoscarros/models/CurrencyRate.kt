package mz.co.macave.mundodoscarros.models

import kotlinx.serialization.SerialName

data class CurrencyRate(
    val provider: String = "",
    @SerialName("WARNING_UPGRADE_TO_V6")
    val warningUpgradeTov6: String = "",
    val terms: String = "",
    val base: String = "",
    val date: String = "",
    @SerialName("last_time_updated")
    val lastTimeUpdated: Long = 0L,
    var rates : MutableMap<String, Double>
)
