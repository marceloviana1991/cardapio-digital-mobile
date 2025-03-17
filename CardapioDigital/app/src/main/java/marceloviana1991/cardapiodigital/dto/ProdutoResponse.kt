package marceloviana1991.cardapiodigital.dto

import java.math.BigDecimal

data class ProdutoResponse(
    val id: Long,
    val nome: String,
    val imagem: String,
    val valor: BigDecimal
) {

}
