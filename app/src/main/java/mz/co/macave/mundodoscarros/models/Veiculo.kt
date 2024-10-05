package mz.co.macave.mundodoscarros.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Veiculo (
    @SerializedName("TipoVeiculo")
    val tipoVeiculo: Int = 0,
    @SerializedName("Valor")
    val valor: String = "",
    @SerializedName("Marca")
    val marca: String = "",
    @SerializedName("Modelo")
    val modelo: String = "",
    @SerializedName("AnoModelo")
    val anoModelo: Int = 0,
    @SerializedName("Combustivel")
    val combustivel: String = "",
    @SerializedName("CodigoFipe")
    val codigoFipe: String = "",
    @SerializedName("MesReferencia")
    val mesReferencia: String = "",
    @SerializedName("SiglaCombustivel")
    val siglaCombustivel: String = ""
)