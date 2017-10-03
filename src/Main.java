import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static List<Mesa> getData(){
        List<Mesa> mesas = new ArrayList<>();
        for(int i=0; i<Mesa.MAX_TABLES; i++){
            mesas.add(new Mesa());
        }
        Random random = new Random();
        List<Item> itens = Item.listAll();
        for(int i=0; i<20; i++){
            Mesa mesa = mesas.get(random.nextInt(mesas.size()));
            Pedido pedido = new Pedido();
            for(int j=0; j<itens.size(); j++){
                int q = random.nextInt(3);
                if(q > 0)
                    pedido.addItem(itens.get(j), q);
            }
            for(Pedido p : mesa.pedidos){
                p.close();
            }
            mesa.addPedido(pedido);
        }
        return mesas;
    }

    public static void main(String args[]){
        JanelaMain janela = new JanelaMain(getData());
        janela.draw();

        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }
}
