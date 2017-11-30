import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void generateData(List<Mesa> mesas){
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
    }

    public static void main(String args[]){
        List<Mesa> mesas;
        File f = new File("data.txt");
        if(f.exists()){
            mesas = Manager.restore();
            for(int i=mesas.size(); i<Mesa.MAX_TABLES; i++){
                mesas.add(new Mesa());
            }
        }else{
            mesas = new ArrayList<>();
            for(int i=0; i<Mesa.MAX_TABLES; i++){
                mesas.add(new Mesa());
            }
            Manager.init(mesas);
            generateData(mesas);
            //Manager.save();
            //if(true) return;
        }
        Manager.ready = true;
        JanelaMain janela = new JanelaMain(mesas);
        janela.draw();

        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }
}
