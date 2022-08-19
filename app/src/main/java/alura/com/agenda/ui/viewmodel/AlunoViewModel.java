package alura.com.agenda.ui.viewmodel;

import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import alura.com.agenda.model.Aluno;
import alura.com.agenda.repository.AlunoRepository;

public class AlunoViewModel extends ViewModel {

    private AlunoRepository alunoRepository;

    public AlunoViewModel(AlunoRepository alunoRepository) {
        Log.i("viewmodel", "criando viewmodel" + alunoRepository);
        this.alunoRepository = alunoRepository;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i("viewmodel", "destruindo viewmodel");
    }

    public void listagemAlunoInicial(ListView alunosListView) {
        Log.i("DEBUGANDOOOOOOO", "Buscando aluno no banco de dados ########");
        alunoRepository.listagemAlunoInicial(alunosListView);
    }

//    public MutableLiveData<List<Aluno>> listagemAlunoInicial(ListView alunosListView) {
//        MutableLiveData<List<Aluno>> listaAluno = new MutableLiveData<List<Aluno>>();
//
//        return listaAluno;
//    }

    public void confirmaExclusaoAluno(AdapterView.AdapterContextMenuInfo menuInfo) {
        alunoRepository.confirmaExclusaoAluno(menuInfo);
    }

    public void listarAlunosAtualizada() {
        alunoRepository.listarAlunosAtualizada();
    }

    public Aluno getAluno() {
        return this.alunoRepository.getAluno();
    }

    public void setAluno(Aluno aluno) {
        this.alunoRepository.setAluno(aluno);
    }

    public void salvaAluno() {
        this.alunoRepository.salvaAluno();
    }
}
