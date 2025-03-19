package marceloviana1991.cardapiodigital.memory

object PedidoMemory {
    private val pedido = ArrayList<ItemPedido>()
    private val pedidoPorNome =  ArrayList<ItemPedidoPorNome>()

    fun adicionar(itemPedido: ItemPedido) {
        this.pedido.add(itemPedido)
    }

    fun limpar() {
        pedido.clear()
        pedidoPorNome.clear()
    }

    fun registrar(): List<ItemPedido> {
        return pedido
    }

    fun adicionarPedidoPorNome(itemPedidoPorNome: ItemPedidoPorNome) {
        pedidoPorNome.add(itemPedidoPorNome)
    }

    fun registrarPedidoPorNome(): List<ItemPedidoPorNome> {
        return pedidoPorNome
    }
}