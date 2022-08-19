package alura.com.agenda.ui.viewmodel;

import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.lifecycle.ViewModel;

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
        Log.i("DEBUGANDOOOOOOO", "listagemAlunoInicial Factory: ");
        alunoRepository.listagemAlunoInicial(alunosListView);
    }

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
