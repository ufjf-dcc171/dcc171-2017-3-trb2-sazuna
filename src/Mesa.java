import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Mesa {
    public static final int MAX_TABLES = 10;
    private static int lastId = 0;
    List<Pedido> pedidos;
    int id;

    public Mesa() {
        this.id = ++Mesa.lastId;
        this.pedidos = new ArrayList<>();
    }

    public Mesa(int id) {
        this.id = id;
        this.pedidos = new ArrayList<>();
        if (id > lastId) lastId = id;
    }

    public boolean isAvailable() {
        for (Pedido p : pedidos) {
            if (p.isOpen()) {
                return false;
            }
        }
        return true;
    }

    public void addPedido(Pedido p) {
        if (!this.pedidos.contains(p)) {
            this.pedidos.add(p);
        }
        Manager.save();
    }

    public String serialize() {
        ArrayList<String> result = new ArrayList<String>();
        result.add(String.valueOf(this.id));
        for (Pedido p : pedidos) {
            result.add("begin PEDIDO");
            result.add(p.serialize());
            result.add("end PEDIDO");
        }
        return String.join("\n", result);
    }

    public static Mesa deserialize(List<String> data) {
        Mesa mesa = new Mesa(Integer.valueOf(data.get(0)));
        for (int i = 2; i < data.size(); i++) {
            ArrayList<String> pData = new ArrayList<>();
            while (!data.get(i).equals("end PEDIDO")) {
                pData.add(data.get(i));
                i++;
            }
            mesa.pedidos.add(Pedido.deserialize(pData));
            i++;
        }
        return mesa;
    }


    @Override
    public String toString() {
        return "Mesa " + this.id;
    }
}
