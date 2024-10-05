package mz.co.macave.mundodoscarros.models

import kotlinx.serialization.Serializable

@Serializable
data class Marca(
    val codigo: String,
    val nome: String
)