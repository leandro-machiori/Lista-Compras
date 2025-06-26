package com.leandro.listacompras.View;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.leandro.listacompras.Controller.ListaController;
import com.leandro.listacompras.Model.Item;
import com.leandro.listacompras.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText addItem;
    Button Adicionar, Limpar;
    ListView listaItens;
    TextView totalItens;
    ListaController controller;
    ArrayAdapter<Item> adapter;
    EditText Preco;
    TextView TotalCompra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addItem = findViewById(R.id.addItem);
        Adicionar = findViewById(R.id.Adicionar);
        Limpar = findViewById(R.id.Limpar);
        listaItens = findViewById(R.id.listaItens);
        totalItens = findViewById(R.id.totalItens);
        Preco = findViewById(R.id.Preco);
        TotalCompra = findViewById(R.id.TotalCompra);
        controller = new ListaController(this);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, controller.getLista());
        listaItens.setAdapter(adapter);


        atualizarContagem();
        atualizarTotal();

        Adicionar.setOnClickListener(v -> {
            String nome = addItem.getText().toString().trim();
            String precoStr = Preco.getText().toString().trim();

            if (nome.isEmpty() || precoStr.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            double preco = Double.parseDouble(precoStr);

            if (controller.adicionarItem(nome, preco)) {
                adapter.notifyDataSetChanged();
                atualizarContagem();
                atualizarTotal();
                addItem.setText("");
                Preco.setText("");
                Toast.makeText(this, "Item adicionado", Toast.LENGTH_SHORT).show();
            }
        });

        listaItens.setOnItemLongClickListener((parent, view, position, id) -> {
            String removido = controller.removerItem(position);
            adapter.notifyDataSetChanged();
            atualizarContagem();
            atualizarTotal();
            Toast.makeText(this, "Item removido: " + removido, Toast.LENGTH_SHORT).show();
            return true;
        });


        Limpar.setOnClickListener(v -> {
            controller.limparLista();
            adapter.notifyDataSetChanged();
            atualizarContagem();
            atualizarTotal();
            Toast.makeText(this, "Lista limpa", Toast.LENGTH_SHORT).show();
        });
    }
    private void atualizarContagem() {
        totalItens.setText("Total de itens: " + controller.getLista().size());
    }

    private void atualizarTotal() {
        double total = 0.0;
        for(int i =0;i<adapter.getCount();i++){
            Item item = adapter.getItem(i);
            if(item != null){
                total += item.getPreco();
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        TotalCompra.setText("Valor Total R$ " + df.format(total));
    }
}