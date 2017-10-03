import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ItemTableModel extends AbstractTableModel {

    Pedido pedido;

    public ItemTableModel(Pedido p) {
        this.pedido = p;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Item";
            case 1:
                return "Quantidade";
            case 2:
                return "Preço";
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public int getRowCount() {
        return pedido.itens.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    public void add(Item item, int quantidade){
        int lastSize = this.pedido.itens.size();
        this.pedido.addItem(item, quantidade);
        if(lastSize < this.pedido.itens.size())
            this.fireTableRowsInserted(this.pedido.itens.size()-1, this.pedido.itens.size()-1);
        else
            this.fireTableDataChanged();
    }

    public void remove(int row){
        this.pedido.itens.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemPedido ip = this.pedido.itens.get(rowIndex);
        switch(columnIndex){
            case 0:
                return ip.getItem();
            case 1:
                return ip.getQuantidade();
            case 2:
                return "R$" + (ip.getItem().getPreco() * ip.getQuantidade());
            default:
                throw new IndexOutOfBoundsException();
        }
    }
}
