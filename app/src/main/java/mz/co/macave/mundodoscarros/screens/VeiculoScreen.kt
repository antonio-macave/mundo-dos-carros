package mz.co.macave.mundodoscarros.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mz.co.macave.mundodoscarros.R
import mz.co.macave.mundodoscarros.models.Veiculo
import mz.co.macave.mundodoscarros.utils.ChosenCurrency
import mz.co.macave.mundodoscarros.utils.Converter

@Composable
fun VeiculoDetails(veiculo: Veiculo) {

    val modifierChain = Modifier
        .fillMaxWidth()
        .padding(
            start = 12.dp,
            bottom = 8.dp
        )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 12.dp
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Marca(marca = veiculo.marca)
            Spacer(modifier = Modifier.weight(1f))
            GasType(gasType = veiculo.combustivel)
        }

        Column(
            modifier = modifierChain,
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Info(info = veiculo.modelo)
            TitleInfo(imageId = R.drawable.calendar_24, title = "Ano", info = veiculo.anoModelo.toString())
            TitleInfo(imageId = R.drawable.price_24, title = "Preço médio", info = "${ChosenCurrency.currency} ${Converter.toOtherCurrency(veiculo.valor, ChosenCurrency.rate)}")
        }
    }
}

@Composable
fun GasType(gasType: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 12.dp,
                    bottomStart = 12.dp,
                )
            )
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(8.dp)
    ) {

        Icon(
            modifier = Modifier
                .size(18.dp),
            painter = painterResource(id = R.drawable.gas_type_24),
            contentDescription = stringResource(id = R.string.gastype),
            tint = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = gasType,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}


@Composable
fun Marca(marca: String) {
    Text(
        modifier = Modifier
            .padding(
                start = 12.dp,
                top = 8.dp,
                bottom = 0.dp
            ),
        text = marca,
        fontSize = 18.sp
    )
}

@Composable
fun Info(info: String) {
    Text(
        text = info,
        fontSize = 18.sp,
    )
}

@Composable
fun TitleInfo(imageId: Int, title: String, info: String) {
    Row {
        Icon(
            modifier = Modifier
                .size(20.dp)
                .fillMaxHeight(0.01f)
                .padding(top = 1.dp),
            painter = painterResource(id = imageId),
            contentDescription = null)

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = info,
                fontSize = 18.sp
            )
        }
    }
}

@Preview(device = "id:pixel_6a")
@Composable
fun Previews() {
    val v = Veiculo(
        tipoVeiculo = 0,
        valor = "MZN 4.350.000,00",
        marca = "Ford",
        modelo = "Ranger Raptor Bi turbo 2.5V",
        anoModelo = 2011,
        combustivel = "Gasolina",
        codigoFipe = "4-1002",
        mesReferencia = "Agosto de 2023",
        siglaCombustivel = "G"
    )
    VeiculoDetails(veiculo = v)
}