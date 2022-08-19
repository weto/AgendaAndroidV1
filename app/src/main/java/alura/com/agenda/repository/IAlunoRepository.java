package alura.com.agenda.repository;

import android.content.Context;
import android.widget.AdapterView;
import android.widget.ListView;

import alura.com.agenda.dao.AlunoDAO;
import alura.com.agenda.ui.adapter.ListaAlunosAdapter;

public interface IAlunoRepository {

    void listagemAlunoInicial(ListView alunosListView);
    void listarAlunosAtualizada();
    void confirmaExclusaoAluno(AdapterView.AdapterContextMenuInfo menuInfo);

    void salvaAluno();
}
