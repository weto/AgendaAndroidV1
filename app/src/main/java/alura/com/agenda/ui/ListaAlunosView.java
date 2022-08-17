package alura.com.agenda.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.AdapterView;

import alura.com.agenda.dao.AlunoDAO;
import alura.com.agenda.model.Aluno;
import alura.com.agenda.ui.adapter.ListaAlunosAdapter;

public class ListaAlunosView {
    private final Context context;
    private final AlunoDAO dao;
    private final ListaAlunosAdapter adapter;

    public ListaAlunosView(Context context, AlunoDAO dao, ListaAlunosAdapter adapter) {
        this.dao = dao;
        this.context = context;
        this.adapter = adapter;
    }

    public void confirmaExclusaoAluno(AdapterView.AdapterContextMenuInfo menuInfo) {
        new AlertDialog.Builder(context)
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

    public void atualizaAlunos() {
        adapter.atualiza(dao.todos());
    }
}
