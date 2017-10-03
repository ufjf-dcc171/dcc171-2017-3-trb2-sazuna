import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
public class JanelaMain extends JFrame{
    List<Mesa> mesas;
    JTable tablePedidos;
    JList listMesas;

    public JanelaMain(List<Mesa> mesas) throws HeadlessException {
        this.setTitle("Restaurante Eleven-Ten");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mesas = mesas;
    }

    public void updateTablePedidos(){
        Mesa m = (Mesa)this.listMesas.getSelectedValue();
        ((PedidoTableModel)this.tablePedidos.getModel()).update(m.pedidos);
    }

    public void selectMesa(Mesa m){
        int index = this.mesas.indexOf(m)  + 1;
        this.listMesas.setSelectedIndex(index);
        ((PedidoTableModel)this.tablePedidos.getModel()).update(m.pedidos);
    }

    public void draw(){
        JPanel buttonPanel = new JPanel();
        this.add(buttonPanel, BorderLayout.NORTH);

        JButton btnOpen = new JButton("Novo Pedido");
        btnOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JanelaPedidoItem(JanelaMain.this, new Pedido());
            }
        });
        JButton btnEdit = new JButton("Editar Pedido");
        btnEdit.setEnabled(false);
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tablePedidos.getSelectedRow();
                PedidoTableModel model = (PedidoTableModel)tablePedidos.getModel();
                new JanelaPedidoItem(JanelaMain.this, (Pedido)model.getValueAt(row, 0),(Mesa)listMesas.getSelectedValue());
            }
        });
        JButton btnClose = new JButton("Fechar Pedido");
        btnClose.setEnabled(false);
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tablePedidos.getSelectedRow();
                PedidoTableModel model = (PedidoTableModel)tablePedidos.getModel();
                Pedido pedido = (Pedido)model.getValueAt(row, 0);
                if(pedido.itens.size() == 0){
                    JOptionPane.showMessageDialog(JanelaMain.this, "O Pedido deve conter pelo menos um Item antes de ser fechado!");
                }else{
                    pedido.close();
                    JanelaMain.this.updateTablePedidos();
                }
            }
        });
        buttonPanel.add(btnOpen);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnClose);

        JPanel mesaPanel = new JPanel();
        Mesa[] mesaData = new Mesa[this.mesas.size()+1];
        mesaData[0] = new Mesa(){
            @Override
            public String toString(){
                return "Todas";
            }
            {
                this.id = -1;
            }
        };
        int i = 1;
        for(Mesa mesa : mesas){
            mesaData[i++] = mesa;
        }
        this.listMesas = new JList<>(mesaData);
        this.listMesas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.listMesas.setLayoutOrientation(JList.VERTICAL);
        this.listMesas.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                List<Pedido> pedidos = new ArrayList<>();
                if(listMesas.getSelectedIndex() == 0){
                    for(Mesa m : mesas){
                        pedidos.addAll(m.pedidos);
                    }
                }else{
                    pedidos = ((Mesa)listMesas.getSelectedValue()).pedidos;
                }
                ((PedidoTableModel)tablePedidos.getModel()).update(pedidos);
            }
        });
        JScrollPane mesaScroller = new JScrollPane(this.listMesas);
        mesaScroller.setPreferredSize(new Dimension(100, 80));
        mesaPanel.add(mesaScroller);

        this.add(mesaScroller, BorderLayout.WEST);

        this.tablePedidos = new JTable(new PedidoTableModel());
        JScrollPane pedidosScroller = new JScrollPane(this.tablePedidos);
        this.add(pedidosScroller, BorderLayout.CENTER);

        this.tablePedidos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (tablePedidos.getSelectedRowCount() == 0) {
                    btnClose.setEnabled(false);
                    btnEdit.setEnabled(false);
                }else{
                    btnClose.setEnabled(true);
                    btnEdit.setEnabled(true);
                }
            }
        });

        this.tablePedidos.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table = JanelaMain.this.tablePedidos;
                int row = table.rowAtPoint(me.getPoint());
                if (row != -1 && me.getClickCount() == 2) {
                    Pedido p = (Pedido)(table.getModel()).getValueAt(row, 0);
                    new JanelaPedidoDetalhe(p);
                }
            }
        });

    }

}
