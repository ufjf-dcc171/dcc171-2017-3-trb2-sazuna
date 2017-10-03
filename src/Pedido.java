import java.util.*;

public class Pedido {
    private static int lastId = 0;
    int id;
    List<ItemPedido> itens;
    Integer mesa;
    Date dateAberto;
    Date dateFechado;

    public Pedido() {
        this.id = Pedido.lastId++;
        this.itens = new ArrayList<>();
        this.dateAberto = new Date();
        this.dateFechado = null;
    }

    public void close() {
        this.dateFechado = new Date();
    }

    public boolean isOpen() {
        return this.dateFechado == null;
    }

    @Override
    public String toString(){
        return "Pedido " + id;
    }

    public void addItem(Item i, Integer q) {
        ItemPedido ip = null;
        for(ItemPedido itemPedido : this.itens){
            if(itemPedido.getItem() == i){
                ip = itemPedido;
                break;
            }
        }
        if(ip != null){
            ip.setQuantidade(q);
        }else{
            this.itens.add(new ItemPedido(i, q));
        }
    }

    public double getTotal(){
        double total = 0;
        for(ItemPedido ip : this.itens){
            total += ip.getQuantidade() * ip.getItem().getPreco();
        }
        return total;
    }

}
