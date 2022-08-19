package alura.com.agenda.repository;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.lifecycle.Observer;

import java.util.List;

import alura.com.agenda.R;
import alura.com.agenda.dao.AlunoDAO;
import alura.com.agenda.model.Aluno;
import alura.com.agenda.ui.ListaAlunosView;
import alura.com.agenda.ui.adapter.ListaAlunosAdapter;
import alura.com.agenda.ui.viewmodel.AlunoViewModel;

public class AlunoRepository implements IAlunoRepository {
    private Context context;
    private AlunoDAO dao = new AlunoDAO();
    private ListaAlunosView listaAlunosView;
    private ListaAlunosAdapter adapter;
    private Aluno aluno;

    public AlunoRepository(Context context) {
        this.context = context;
        adapter = new ListaAlunosAdapter(context);
        listaAlunosView = new ListaAlunosView(context, dao, adapter);
    }

    @Override
    public void listagemAlunoInicial(ListView alunosListView) {
        ListaAlunosAdapter adapter = new ListaAlunosAdapter(context);
        listaAlunosView = new ListaAlunosView(context, dao, adapter);
        alunosListView.setAdapter(adapter);
        Log.i("Carregamento inicial", "listagemAlunoInicial: ");
    }

    @Override
    public void listarAlunosAtualizada() {
        listaAlunosView.atualizaAlunos();
        Log.i("Atualizando lista", "listarAlunosAtualizada: ");
    }

    @Override
    public void confirmaExclusaoAluno(AdapterView.AdapterContextMenuInfo menuInfo) {
        listaAlunosView.confirmaExclusaoAluno(menuInfo);
    }

    @Override
    public void salvaAluno() {
        if(getAluno().getId() == null) {
            dao.salvar(getAluno());
        } else {
            dao.editar(getAluno());
        }
    }

    public Aluno getAluno(){
        return this.aluno;
    }

    public void setAluno(Aluno aluno){
        this.aluno = aluno;
    }
}
