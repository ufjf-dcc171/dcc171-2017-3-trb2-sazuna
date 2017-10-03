import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class JanelaPedidoItem extends JFrame {

    Pedido pedido;
    JanelaMain janelaMain;

    public JanelaPedidoItem(JanelaMain janelaMain, Pedido p, Mesa m){
        super("Pedido: " + p.id);

        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.pedido = p;
        this.janelaMain = janelaMain;


        JPanel inputPane = new JPanel();
        inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.Y_AXIS));

        Vector<Mesa> mesaOptions = new Vector<>();
        if(m == null) {
            for (Mesa mesa : janelaMain.mesas) {
                if (mesa.isAvailable()) {
                    mesaOptions.add(mesa);
                }
            }
            if (mesaOptions.size() == 0) {
                JOptionPane.showMessageDialog(this, "Não há mesas disponíveis!");
                this.dispose();
                return;
            }
        }else{
            mesaOptions.add(m);
        }
        JComboBox<Mesa> mesaCombo = new JComboBox<>(mesaOptions);

        JLabel mesaComboLabel = new JLabel("Pedido para: ");

        JPanel mesaComboPanel = new JPanel();
        mesaComboPanel.add(mesaComboLabel);
        mesaComboPanel.add(mesaCombo);

        inputPane.add(mesaComboPanel);

        JPanel itemPane = new JPanel();
        JComboBox<Item> itemCombo = new JComboBox<>(new Vector<>(Item.listAll()));
        itemCombo.setSelectedIndex(-1);

        JTextField quantText = new JTextField(4);

        itemPane.add(new JLabel("Item"));
        itemPane.add(itemCombo);
        itemPane.add(quantText);
        inputPane.add(itemPane);

        JPanel paneTableActions = new JPanel();
        paneTableActions.setLayout(new GridLayout(1, 3));

        JButton btnAdd = new JButton("Adicionar");
        JButton btnRemove = new JButton("Remover");
        btnRemove.setEnabled(false);

        paneTableActions.add(btnAdd);
        paneTableActions.add(new JPanel());
        paneTableActions.add(btnRemove);

        inputPane.add(paneTableActions);

        JTable table = new JTable(new ItemTableModel(this.pedido));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroller = new JScrollPane(table);
        scroller.setPreferredSize(new Dimension(400, 150));
        JButton btnOk = new JButton("Sair");
        JButton btnClose = new JButton("Fechar Pedido");

        JLabel lblTotal = new JLabel("Total: R$" + this.pedido.getTotal());

        JPanel paneButtons = new JPanel();
        paneButtons.setLayout(new GridLayout(1, 3));
        paneButtons.add(lblTotal);
        paneButtons.add(btnOk);
        paneButtons.add(btnClose);

        JPanel pane = new JPanel();
        pane.add(inputPane);
        pane.add(scroller);
        pane.add(paneButtons);

        this.add(pane);

        if(!pedido.isOpen()){
            btnAdd.setEnabled(false);
            btnRemove.setEnabled(false);
            btnClose.setEnabled(false);
        }

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Item item = (Item)itemCombo.getSelectedItem();
                    if(item == null){
                        JOptionPane.showMessageDialog(JanelaPedidoItem.this, "[Item] deve ser selecionado!");
                        return;
                    }
                    int quant = Integer.valueOf(quantText.getText());
                    if(quant < 1){
                        JOptionPane.showMessageDialog(JanelaPedidoItem.this, "[Quantidade] deve ser maior que 0!");
                        return;
                    }
                    ((ItemTableModel)table.getModel()).add(item, quant);

                    itemCombo.setSelectedIndex(-1);
                    quantText.setText("");
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(JanelaPedidoItem.this, "[Quantidade] deve ser um número!");
                }

            }
        });

        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((ItemTableModel)table.getModel()).remove(table.getSelectedRow());
            }
        });

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (table.getSelectedRowCount() == 0) {
                    btnRemove.setEnabled(false);
                }else if(pedido.isOpen()){
                    ItemTableModel model = (ItemTableModel) table.getModel();
                    btnRemove.setEnabled(true);
                    itemCombo.setSelectedItem(model.getValueAt(table.getSelectedRow(), 0));
                    quantText.setText(String.valueOf(model.getValueAt(table.getSelectedRow(), 1)));

                    itemCombo.requestFocus();
                }
            }
        });

        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                lblTotal.setText("Total: R$" + JanelaPedidoItem.this.pedido.getTotal());
            }
        });

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleOk((Mesa)mesaCombo.getSelectedItem());
            }
        });

        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JanelaPedidoItem.this.pedido.itens.size() == 0){
                    JOptionPane.showMessageDialog(JanelaPedidoItem.this, "O Pedido deve conter pelo menos um Item antes de ser fechado!");
                    return;
                }
                JanelaPedidoItem.this.pedido.close();
                handleOk((Mesa)mesaCombo.getSelectedItem());
                new JanelaPedidoDetalhe(JanelaPedidoItem.this.pedido);
            }
        });
        this.setVisible(true);
    }

    public JanelaPedidoItem(JanelaMain janelaMain, Pedido p) throws HeadlessException {
        this(janelaMain, p, null);
    }

    public void handleOk(Mesa selectedMesa){
        selectedMesa.addPedido(pedido);
        JanelaPedidoItem.this.setVisible(false);
        JanelaPedidoItem.this.dispose();
        janelaMain.selectMesa(selectedMesa);
    }
}
