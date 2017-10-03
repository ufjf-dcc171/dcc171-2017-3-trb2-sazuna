import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class JanelaPedidoDetalhe extends JFrame {

    public JanelaPedidoDetalhe(Pedido pedido) throws HeadlessException {
        super("Pedido" + pedido.id);
        this.setLocationRelativeTo(null);
        setSize(250, 400);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String text = "Pedido " + pedido.id + "\n\n";
        for(ItemPedido ip : pedido.itens){
            for(int i=0; i<ip.getQuantidade();i++){
                text += ip.getItem().getNome();
                for(int j=ip.getItem().getNome().length();j<20;j++){
                    text += ".";
                }
                String preco = formatter.format(ip.getItem().getPreco());
                text +="R" + preco +"\n";
            }
        }
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        text += "\nTotal: R" + formatter.format(pedido.getTotal());
        text += "\nAberto em: " + dt.format(pedido.dateAberto);
        text += "\nFechado em: " + (pedido.dateFechado == null ? "--" : dt.format(pedido.dateFechado));
        JTextArea area = new JTextArea(text);
        area.setEditable(false);
        Font font = new Font("Courier New", Font.PLAIN,12);
        area.setFont(font);
        add(area);
        setVisible(true);
    }
}
