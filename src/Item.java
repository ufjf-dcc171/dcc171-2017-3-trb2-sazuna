import java.util.ArrayList;

public class Item {
    String nome;
    Double preco;

    public Item(String nome, Double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public Double getPreco() {
        return preco;
    }

    @Override
    public String toString(){
        return this.nome;
    }

    static final ArrayList<Item> todos = new ArrayList(){
        {
            add(new Item("Coca-Cola", 3.5));
            add(new Item("Suco Natural", 2.25));
            add(new Item("X-Tudo", 12.5));
            add(new Item("X-Bacon", 9.00));
            add(new Item("X-Egg",8.00));
            add(new Item("Sorvete", 2.75));
            add(new Item("Especial da Casa", 5.00));
        }
    };

    public static ArrayList<Item> listAll(){
        return Item.todos;
    }
}
