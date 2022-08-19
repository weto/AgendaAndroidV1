package alura.com.agenda.ui.activity;

import android.content.Intent;
import android.database.Observable;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import alura.com.agenda.R;
import alura.com.agenda.model.Aluno;
import alura.com.agenda.repository.AlunoRepository;
import alura.com.agenda.ui.viewmodel.AlunoViewModel;
import alura.com.agenda.ui.viewmodel.factory.AlunoViewModelFactor;

public class ListaAlunosActivity extends AppCompatActivity implements Constantes {
    private ListView alunosListView;
    private AlunoViewModel provedor;
    private AlunoViewModelFactor factory;
    private Observer<List<Aluno>> nameObserver;

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
        botaoNovoAluno.setOnClickListener(v -> startActivity(new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        provedor.listarAlunosAtualizada();
    }

    private void carregaTodosalunos() {
        carregaDados();
        alteraAluno();
    }

    private void carregaDados() {
        alunosListView = findViewById(R.id.activity_lista_alunos_listview);
        this.factory = new AlunoViewModelFactor(new AlunoRepository(this));
        this.provedor = new ViewModelProvider(this, factory).get(AlunoViewModel.class);
        chamadaLiveData();
        this.provedor.listagemAlunoInicial(alunosListView);
        registerForContextMenu(alunosListView);
    }

    private void chamadaLiveData() {
//        Depois enteder como funciona live data
//        nameObserver = new Observer<List<Aluno>>(){
//            @Override
//            public void onChanged(List<Aluno> alunos) {
//                alunos.add(new Aluno("teste","sdsd","testee"));
//                Log.i("nameObserver", "onChanged: "+alunos.size());
//            }
//        };
//        this.provedor.listagemAlunoInicial(alunosListView).observe(this, nameObserver);
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int itemId = item.getItemId();
        if(itemId == R.id.activity_lista_alunos_menu_remover) {
            this.provedor.confirmaExclusaoAluno(menuInfo);
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