import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PedidoTableModel extends AbstractTableModel {

    List<Pedido> pedidos;

    public PedidoTableModel(){
        this.pedidos = new ArrayList<>();
    }

    public void update(List<Pedido> pedidos){
        this.pedidos = pedidos;
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return pedidos.size();
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Pedido";
            case 1:
                return "Aberto em";
            case 2:
                return "Fechado em";
            case 3:
                return "Total";
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pedido p = this.pedidos.get(rowIndex);
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        switch(columnIndex){
            case 0:
                return p;
            case 1:
                return dt.format(p.dateAberto);
            case 2:
                return p.dateFechado == null ? "" : dt.format(p.dateFechado);
            case 3:
                return "R$" + p.getTotal();
            default:
                throw new IndexOutOfBoundsException();
        }
    }
}
