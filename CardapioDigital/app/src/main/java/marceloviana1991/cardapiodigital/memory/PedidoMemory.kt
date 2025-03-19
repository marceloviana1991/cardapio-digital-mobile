package marceloviana1991.cardapiodigital.memory

object PedidoMemory {
    private val pedido = ArrayList<ItemPedido>()

    fun adicionar(itemPedido: ItemPedido) {
        this.pedido.add(itemPedido)
    }

    fun limpar() {
        pedido.clear()
    }

    fun registrar(): List<ItemPedido> {
        return pedido
    }
}