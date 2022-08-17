package alura.com.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import alura.com.agenda.R;
import alura.com.agenda.dao.AlunoDAO;
import alura.com.agenda.model.Aluno;

public class ListaAlunosActivity extends AppCompatActivity implements Constantes {
    private final AlunoDAO dao = new AlunoDAO();
    private ListView alunosListView;
    private ArrayAdapter<Aluno> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        setTitle(TITULO_APPBAR_PRINCIPAL);
        carregaTodosalunos();
        configuraFabNovoAluno();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    private void configuraFabNovoAluno() {
        abreFormularioNovoAluno();
    }

    private void abreFormularioNovoAluno() {
        FloatingActionButton botaoNovoAluno = findViewById(R.id.activity_main_lista_alunos_fab_novo_aluno);
        botaoNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaAlunos();
    }

    private void atualizaAlunos() {
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    private void carregaTodosalunos() {
        carregaDados();
        alteraAluno();
    }

    private void carregaDados() {
        alunosListView = findViewById(R.id.activity_lista_alunos_listview);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                dao.todos());
        alunosListView.setAdapter(adapter);
        registerForContextMenu(alunosListView);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int itemId = item.getItemId();
        if(itemId == R.id.activity_lista_alunos_menu_remover) {
            Aluno alunoEscolhido = adapter.getItem(menuInfo.position);
            remove(alunoEscolhido);
        }
        return super.onContextItemSelected(item);
    }

    private void remove(Aluno alunoSelecionado) {
        dao.delete(alunoSelecionado);
        adapter.remove(alunoSelecionado);
    }

    private void alteraAluno() {
        alunosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class);
                Aluno alunoSelecionado = (Aluno) parent.getItemAtPosition(position);
                intent.putExtra(CHAVE_ALUNO, alunoSelecionado);
                startActivity(intent);
            }
        });
    }
}
