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
        for (String nome : itensSalvos) {
            lista.add(new Item(nome));
        }
    }
        public ArrayList<Item> getLista() {
            return lista;
        }
        public boolean adicionarItem(String item) {
            if (item.isEmpty()) return false;
            lista.add(new Item(item));
            salvarLista();
            return true;
        }
        public String removerItem(int posicao) {
            String removido = lista.remove(posicao).getNome();
            salvarLista();
            return removido;
        }
        public void limparLista() {
            lista.clear();
            salvarLista();
        }
    private void salvarLista() {
        Set<String> set = new HashSet<>();
        for (Item item : lista) {
            set.add(item.getNome());
        }
        preferences.edit().putStringSet(LIST_KEY, set).apply();
    }
}