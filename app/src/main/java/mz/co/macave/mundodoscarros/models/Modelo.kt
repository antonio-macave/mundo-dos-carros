package mz.co.macave.mundodoscarros.models

import kotlinx.serialization.Serializable

@Serializable
data class Modelo(
    val codigo: Int,
    val nome: String,
)