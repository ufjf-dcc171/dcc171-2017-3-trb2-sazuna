import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Pedido {
    private static int lastId = 0;
    int id;
    List<ItemPedido> itens;
    Integer mesa;
    Date dateAberto;
    Date dateFechado;

    public Pedido() {
        this.id = ++Pedido.lastId;
        this.itens = new ArrayList<>();
        this.dateAberto = new Date();
        this.dateFechado = null;
        Manager.save();
    }

    public Pedido(int id) {
        this.id = id;
        if (id > lastId) lastId = id;
    }

    public void close() {
        this.dateFechado = new Date();
        Manager.save();
    }

    public boolean isOpen() {
        return this.dateFechado == null;
    }

    @Override
    public String toString() {
        return "Pedido " + id;
    }

    public void addItem(Item i, Integer q) {
        ItemPedido ip = null;
        for (ItemPedido itemPedido : this.itens) {
            if (itemPedido.getItem() == i) {
                ip = itemPedido;
                break;
            }
        }
        if (ip != null) {
            ip.setQuantidade(q);
        } else {
            this.itens.add(new ItemPedido(i, q));
        }
        Manager.save();
    }

    public double getTotal() {
        double total = 0;
        for (ItemPedido ip : this.itens) {
            total += ip.getQuantidade() * ip.getItem().getPreco();
        }
        return total;
    }

    public String serialize() {
        ArrayList<String> data = new ArrayList<String>();
        data.add(String.valueOf(id));
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        data.add(sd.format(dateAberto));
        data.add(dateFechado == null ? "" : sd.format(dateFechado));
        for (ItemPedido ip : itens) {
            data.add(ip.serialize());
        }
        return String.join("\n", data);
    }

    public static Pedido deserialize(List<String> data) {
        Pedido p = new Pedido();
        try {
            p.id = Integer.valueOf(data.get(0));
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            p.dateAberto = sd.parse(data.get(1));
            String f = data.get(2);
            if (!f.isEmpty()) {
                p.dateFechado = sd.parse(f);
            }
            for (int i = 3; i < data.size(); i++) {
                p.itens.add(ItemPedido.deserialize(data.get(i)));
            }
        } catch (ParseException e) {
            System.out.println("Erro ao ler data do Pedido " + p.id);
        }
        return p;
    }

}
