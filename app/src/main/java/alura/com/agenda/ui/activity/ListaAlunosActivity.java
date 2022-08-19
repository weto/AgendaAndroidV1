package alura.com.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import alura.com.agenda.R;
import alura.com.agenda.dao.AlunoDAO;
import alura.com.agenda.model.Aluno;
import alura.com.agenda.repository.AlunoRepository;
import alura.com.agenda.ui.ListaAlunosView;
import alura.com.agenda.ui.adapter.ListaAlunosAdapter;
import alura.com.agenda.ui.viewmodel.AlunoViewModel;

public class ListaAlunosActivity extends AppCompatActivity implements Constantes {
    private ListView alunosListView;
    private AlunoViewModel provedor;
    private AlunoRepository alunoRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        setTitle(TITULO_APPBAR_PRINCIPAL);
        carregaTodosalunos();
        configuraFabNovoAluno();
        provedor = new ViewModelProvider(this).get(AlunoViewModel.class);
        Log.i("viewmodel", "onCreate: "+ provedor.toString());
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
        botaoNovoAluno.setOnClickListener(v -> startActivity(new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        alunoRepository.listarAlunosAtualizada();
    }

    private void carregaTodosalunos() {
        carregaDados();
        alteraAluno();
    }

    private void carregaDados() {
        alunosListView = findViewById(R.id.activity_lista_alunos_listview);
        alunoRepository = new AlunoRepository(this);
        alunoRepository.listagemAlunoInicial(alunosListView);
        registerForContextMenu(alunosListView);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int itemId = item.getItemId();
        if(itemId == R.id.activity_lista_alunos_menu_remover) {
            alunoRepository.confirmaExclusaoAluno(menuInfo);
        }
        return super.onContextItemSelected(item);
    }

    private void alteraAluno() {
        alunosListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class);
            Aluno alunoSelecionado = (Aluno) parent.getItemAtPosition(position);
            intent.putExtra(CHAVE_ALUNO, alunoSelecionado);
            startActivity(intent);
        });
    }
}