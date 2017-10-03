public class ItemPedido {
    Item item;
    Integer quantidade;

    public ItemPedido(Item item, Integer quantidade) {
        this.item = item;
        this.quantidade = quantidade;
    }

    public Item getItem() {
        return item;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
    }
}


