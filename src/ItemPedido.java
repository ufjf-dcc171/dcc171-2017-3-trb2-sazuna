import java.util.ArrayList;

public class ItemPedido {
    Integer quantidade;
    String nome;
    Double preco;

    public ItemPedido(Item item, Integer quantidade) {
        this.nome = item.getNome();
        this.preco = item.getPreco();
        this.quantidade = quantidade;
    }

    private ItemPedido(String nome, Double preco, Integer quantidade){
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Item getItem() {
        return new Item(nome, preco);
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
    }

    public String serialize(){
        return String.format("%s\t%.2f\t%d", this.nome, this.preco, this.quantidade);
    }

    public static ItemPedido deserialize(String data){
        String[] list = data.split("\t");
        return new ItemPedido(list[0], Double.valueOf(list[1]), Integer.valueOf(list[2]));
    }
}


