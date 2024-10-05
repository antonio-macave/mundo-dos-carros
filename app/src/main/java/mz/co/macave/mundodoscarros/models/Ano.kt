package mz.co.macave.mundodoscarros.models

import kotlinx.serialization.Serializable

@Serializable
data class Ano (
    val codigo: String,
    val nome: String
)