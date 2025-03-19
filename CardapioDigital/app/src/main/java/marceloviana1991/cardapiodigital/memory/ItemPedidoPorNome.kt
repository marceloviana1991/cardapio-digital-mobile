package marceloviana1991.cardapiodigital.memory

data class ItemPedidoPorNome(val quantidade: Int, val nome: String) {
    override fun toString(): String {
        return "$nome - $quantidade UND"
    }
}