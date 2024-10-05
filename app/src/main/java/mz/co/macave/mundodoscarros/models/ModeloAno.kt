package mz.co.macave.mundodoscarros.models

import kotlinx.serialization.Serializable

@Serializable
data class ModeloAno(
    val modelos: List<Modelo>?,
    val anos: List<Ano>?
)