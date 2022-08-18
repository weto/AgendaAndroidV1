package alura.com.agenda.ui.viewmodel;

import android.content.Intent;
import android.util.Log;
import android.widget.ListView;

import androidx.lifecycle.ViewModel;

import alura.com.agenda.R;
import alura.com.agenda.dao.AlunoDAO;
import alura.com.agenda.model.Aluno;
import alura.com.agenda.ui.ListaAlunosView;
import alura.com.agenda.ui.activity.FormularioAlunoActivity;
import alura.com.agenda.ui.activity.ListaAlunosActivity;
import alura.com.agenda.ui.adapter.ListaAlunosAdapter;

public class AlunoViewModel extends ViewModel {

    private final AlunoDAO dao = new AlunoDAO();
    private ListView alunosListView;
    private ListaAlunosView listaAlunosView;


    public AlunoViewModel() {
        Log.i("viewmodel", "criando viewmodel");
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i("viewmodel", "destruindo viewmodel");
    }






}
