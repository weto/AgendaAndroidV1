package alura.com.agenda.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import alura.com.agenda.R;
import alura.com.agenda.dao.AlunoDAO;
import alura.com.agenda.model.Aluno;
import alura.com.agenda.ui.adapter.ListaAlunosAdapter;

public class ListaAlunosActivity extends AppCompatActivity implements Constantes {
    private final AlunoDAO dao = new AlunoDAO();
    private ListView alunosListView;
    private ListaAlunosAdapter adapter;

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
        adapter.atualiza(dao.todos());
    }

    private void carregaTodosalunos() {
        carregaDados();
        alteraAluno();
    }

    private void carregaDados() {
        alunosListView = findViewById(R.id.activity_lista_alunos_listview);
        adapter = new ListaAlunosAdapter(this);
        alunosListView.setAdapter(adapter);
        registerForContextMenu(alunosListView);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int itemId = item.getItemId();
        if(itemId == R.id.activity_lista_alunos_menu_remover) {
            confirmaExclusaoAluno(menuInfo);
        }
        return super.onContextItemSelected(item);
    }

    private void confirmaExclusaoAluno(AdapterView.AdapterContextMenuInfo menuInfo) {
        new AlertDialog.Builder(this)
            .setTitle("Tem certeza que deseja excluir o aluno?")
            .setPositiveButton("Sim", (dialog, which) -> {
                Aluno alunoEscolhido = (Aluno) adapter.getItem(menuInfo.position);
                remove(alunoEscolhido);
            })
            .setNegativeButton("Não", null)
            .show();
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