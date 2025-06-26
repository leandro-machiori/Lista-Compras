package com.leandro.listacompras.Controller;
import android.content.Context;
import android.content.SharedPreferences;
import com.leandro.listacompras.Model.Item;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
public class ListaController {
    private static final String PREFS_NAME = "APP_COMPRAS";
    private static final String LIST_KEY = "LISTA_COMPRAS";
    private SharedPreferences preferences;
    private ArrayList<Item> lista;

    public ListaController(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> itensSalvos = preferences.getStringSet(LIST_KEY, new HashSet<>());

        lista = new ArrayList<>();
        for (String itemString : itensSalvos) {
            String[] partes = itemString.split(";;");
            if (partes.length == 2) {
                String nome = partes[0];
                double preco;
                try {
                    preco = Double.parseDouble(partes[1]);
                } catch (NumberFormatException e) {
                    preco = 0.0;
                }
                lista.add(new Item(nome, preco));
            }
        }
    }

    public ArrayList<Item> getLista() {
        return lista;
    }

    public boolean adicionarItem(String item, double preco) {
        if (item.isEmpty()) return false;
        lista.add(new Item(item, preco));
        salvarLista();
        return true;
    }

    public String removerItem(int posicao) {
        String removido = lista.get(posicao).getNome();
        lista.remove(posicao);
        salvarLista();
        return removido;
    }

    public void limparLista() {
        lista.clear();
        salvarLista();
    }

    public double calcularTotal() {
        double total = 0.0;
        for (Item item : lista) {
            total += item.getPreco();
        }
        return total;
    }

    private void salvarLista() {
        Set<String> set = new HashSet<>();
        for (Item item : lista) {
            set.add(item.getNome() + ";;" + item.getPreco());
        }
        preferences.edit().putStringSet(LIST_KEY, set).apply();
    }
}
