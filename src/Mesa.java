import java.util.ArrayList;
import java.util.List;

public class Mesa {
    public static final int MAX_TABLES = 10;
    private static int lastId = 0;
    List<Pedido> pedidos;
    int id;

    public Mesa(){
        this.id = ++Mesa.lastId;
        this.pedidos = new ArrayList<>();
    }

    public boolean isAvailable(){
        for(Pedido p : pedidos){
            if(p.isOpen()){
                return false;
            }
        }
        return true;
    }

    public void addPedido(Pedido p){
        if(!this.pedidos.contains(p)) {
            this.pedidos.add(p);
        }
    }

    @Override
    public String toString(){
        return "Mesa " + this.id;
    }
}
