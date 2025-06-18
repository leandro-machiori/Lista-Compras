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
import com.leandro.listacompras.R;
import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {
    EditText addItem;
    Button Adicionar, Limpar;
    ListView listaItens;
    TextView totalItens;
    ListaController controller;
    ArrayAdapter<String> adapter;
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
        addItem = findViewById(R.id.additem);
        Adicionar = findViewById(R.id.Adicionar);
        Limpar = findViewById(R.id.Limpar);
        listaItens = findViewById(R.id.listaItens);
        totalItens = findViewById(R.id.totalItens);

        controller = new ListaController(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, controller.getLista());
        listaItens.setAdapter(adapter);
        atualizarContagem();

        Adicionar.setOnClickListener(v -> {
            String item = addItem.getText().toString().trim();
            if (controller.adicionarItem(item)) {
                adapter.notifyDataSetChanged();
                atualizarContagem();
                addItem.setText("");
                Toast.makeText(this, "Item adicionado", Toast.LENGTH_SHORT).show();
            }
        });

        listaItens.setOnItemLongClickListener((parent, view, position, id) -> {
            String removido = controller.removerItem(position);
            adapter.notifyDataSetChanged();
            atualizarContagem();
            Toast.makeText(this, "Item removido: " + removido, Toast.LENGTH_SHORT).show();
            return true;
        });

        Limpar.setOnClickListener(v -> {
            controller.limparLista();
            adapter.notifyDataSetChanged();
            atualizarContagem();
            Toast.makeText(this, "Lista limpa", Toast.LENGTH_SHORT).show();
        });
    }

    private void atualizarContagem() {
        totalItens.setText("Total de itens: " + controller.getLista().size());
    }
}